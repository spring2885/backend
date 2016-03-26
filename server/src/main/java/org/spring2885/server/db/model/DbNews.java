package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class DbNews {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	private Date posted;
	private Date expired;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="person_id")
    private DbPerson person;
	private Long views;
	@Column(nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean active;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public DbPerson getPerson() {
        return person;
    }

    public void setPersonId(DbPerson personId) {
        this.person = personId;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
 // Since we won't have this object outside without the ID, we're ok
 	// See https://developer.jboss.org/wiki/EqualsandHashCode
 	@Override
 	public int hashCode() {
 		return id.hashCode();
 	}

 	@Override
 	public boolean equals(Object obj) {
 		if (this == obj) {
 			return true;
 		}
 		if (!(obj instanceof DbNews)) {
 			return false;
 		}
 		DbNews that = (DbNews) obj;
 		return this.id.equals(that.id);
 	}

    @Override
    public String toString() {
        return "DbNews [id=" + id + ", title=" + title + ", description=" + description + ", posted=" + posted
                + ", expired=" + expired + ", personId=" + person + ", views=" + views + "]";
    }

}
