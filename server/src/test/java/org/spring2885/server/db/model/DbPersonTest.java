package org.spring2885.server.db.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DbPersonTest {
    private DbPerson admin;
    private DbPerson user;
    private DbPerson userExplicit;
    
    @Before
    public void before() {
        admin = new DbPerson(1, "admin@", "Admin");
        admin.addRoleForTesting("USER");
        admin.addRoleForTesting("ADMIN");
        user = new DbPerson(2, "user@", "User");
        userExplicit = new DbPerson(2, "user@explicit-role.com", "User Explicit");
    }

    @Test
    public void isAdmin() {
        assertTrue(admin.isAdmin());
        assertFalse(user.isAdmin());
        assertFalse(userExplicit.isAdmin());
    }
}
