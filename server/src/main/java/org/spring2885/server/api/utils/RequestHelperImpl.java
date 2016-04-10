package org.spring2885.server.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;

@Component
public class RequestHelperImpl implements RequestHelper {
    private static final Logger logger = LoggerFactory.getLogger(RequestHelperImpl.class);

    @Autowired
    private PersonService personService;

    @Override
    public boolean checkAdminRequestIfNeeded(long ownerId, SecurityContextHolderAwareRequestWrapper request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return true;
        }
        // Only admin's can change other profiles.
        String loggedInUserName = request.getUserPrincipal().getName();
        logger.info("checkAdminRequestIfNeeded for object owned by: {}, loggedInUser={}", ownerId, loggedInUserName);
        DbPerson me = personService.findByEmail(loggedInUserName);
        if (me == null) {
            // I can't find myself... need more zen.
            return false;
        }
        
        return me.getId() == ownerId;
    }
    
    /**
     * Checks to see that the {@code request} is either from an ADMIN user, or is from
     * the user who owns the object owned by {@code ownerEmail}.
     */
    public boolean checkAdminRequestIfNeeded(String ownerEmail, SecurityContextHolderAwareRequestWrapper request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return true;
        }
        // Only admin's can change other profiles.
        String loggedInUserName = request.getUserPrincipal().getName();
        logger.info("checkAdminRequestIfNeeded for object owned by: {}, loggedInUser={}",
                ownerEmail, loggedInUserName);
        return StringUtils.equalsIgnoreCase(loggedInUserName, ownerEmail);
    }
    
    @Override
    public boolean isAdminRequest(SecurityContextHolderAwareRequestWrapper request) {
        return request.isUserInRole("ROLE_ADMIN");
    }

    public DbPerson loggedInUser(SecurityContextHolderAwareRequestWrapper request) {
        return personService.findByEmail(request.getUserPrincipal().getName());
    }
}
