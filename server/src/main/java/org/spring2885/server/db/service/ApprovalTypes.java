package org.spring2885.server.db.service;

public class ApprovalTypes {
    // Approval Types
    public static final String FACULTY = "FACULTY";
    public static final String ABUSE = "ABUSE";
    
    public static boolean isValidType(String type) {
        return type.equals(FACULTY) || type.equals(ABUSE);
    }
    
    // Item Types.
    public static final String PERSON = "PERSON";
}
