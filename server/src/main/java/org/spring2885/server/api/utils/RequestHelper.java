package org.spring2885.server.api.utils;

import org.spring2885.server.db.model.DbPerson;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

public interface RequestHelper {

    /**
     * Checks to see that the {@code request} is either from an ADMIN user, or is from
     * the user who owns the object owned by {@code ownerId}.
     */
    boolean checkAdminRequestIfNeeded(long ownerId, SecurityContextHolderAwareRequestWrapper request);
    
    /**
     * Checks to see that the {@code request} is either from an ADMIN user, or is from
     * the user who owns the object owned by {@code ownerEmail}.
     */
    boolean checkAdminRequestIfNeeded(String ownerEmail, SecurityContextHolderAwareRequestWrapper request);
    
    /**
     * Checks to see that the {@code request} is from an ADMIN user.
     */
    boolean isAdminRequest(SecurityContextHolderAwareRequestWrapper request);
    
    /**
     * Gets the currently logged in user.
     */
    DbPerson loggedInUser(SecurityContextHolderAwareRequestWrapper request);
}