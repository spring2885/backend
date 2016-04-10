package org.spring2885.server.db.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.ImmutableSet;

@Cacheable
@Entity
@Table(name="news")
public class DbNews {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String description;
	
	@Column(nullable=false, updatable=false)
	private Date posted;
	private Date expired;

    @JoinColumn(name="person_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private DbPerson person;
	
	private Long views;
	
	@ManyToMany
	@JoinTable(name = "news_visibility",
	    joinColumns = @JoinColumn(name="news", referencedColumnName="id"),
	    inverseJoinColumns = @JoinColumn(name="person_type", referencedColumnName="id"))
	Set<DbPersonType> visibleToPersonTypes;
	
	
	// Mark this as not insertable so the default database value will be used.
	@Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean active;
    
	// Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean abuse;
    
    @OneToMany(mappedBy="news", fetch=FetchType.EAGER)
    private List<DbNewsComment> comments;
    
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isAbuse() {
        return abuse;
    }

    public void setAbuse(Boolean abuse) {
        this.abuse = abuse;
    }
    
    public Set<DbPersonType> getVisibleToPersonTypes() {
        return visibleToPersonTypes;
    }

    public void setVisibleToPersonTypes(Set<DbPersonType> visibleToPersonTypes) {
        this.visibleToPersonTypes = visibleToPersonTypes;
    }

    public void setVisibleToPersonType(DbPersonType visibleToPersonType) {
        this.visibleToPersonTypes = ImmutableSet.of(visibleToPersonType);
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
                + ", expired=" + expired + ", personId=" + person + ", views=" + views
                + " visibleTo=" + visibleToPersonTypes
                + "]";
    }

    public List<DbNewsComment> getComments() {
        return comments;
    }

}
