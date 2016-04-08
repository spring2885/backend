package org.spring2885.model;

import java.security.Principal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for the {@code /user} service.
 */
public class User {
    private Principal principal;
    private Person person;
    
    public User(Principal principal, Person person) {
        this.principal = principal;
        this.person = person;
    }

    @JsonProperty("principal")
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    
}
