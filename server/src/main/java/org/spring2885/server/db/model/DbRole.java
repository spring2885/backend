package org.spring2885.server.db.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Cacheable
@Entity(name="roles")
public class DbRole implements Serializable {
    private static final long serialVersionUID = 1L;

    public DbRole() {}
    
    public DbRole(DbPerson person, String rolename) {
        this.person = person;
        this.rolename = rolename;
    }

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private DbPerson person;
    
    @Id
    private String rolename;
    
    public String rolename() { return rolename; }
    
    @Override
    public String toString() {
        return rolename;
    }
    
}
