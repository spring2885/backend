package org.spring2885.server.api.utils;

import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;

@Component
public class RequestHelperImpl implements RequestHelper {
    @Autowired
    private PersonService personService;

    @Override
    public boolean checkAdminRequestIfNeeded(int ownerId, SecurityContextHolderAwareRequestWrapper request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            // Only admin's can change other profiles.
            String ownerName = request.getUserPrincipal().getName();
            DbPerson me = personService.findByEmail(ownerName);
            if (me == null) {
                // I can't find myself... need more zen.
                return false;
            }
            
            if (me.getId() != ownerId) {
                // Someone is being naughty...
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isAdminRequest(SecurityContextHolderAwareRequestWrapper request) {
        return request.isUserInRole("ROLE_ADMIN");
    }

}
