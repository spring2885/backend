package org.spring2885.server.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="templates")
public class DbTemplate {
    @Id
    private String id;
    private String body;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
}
