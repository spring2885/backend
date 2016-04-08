package org.spring2885.server.db.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

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

@Entity
@Table(name="job")
public class DbJob {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	
	private String title;
	private Integer industry;
	private String location;
	private String description;
	private String company;
	private Integer jobType;
	private Integer postedByPersonId;
	private Integer hours;
	

	@Column(nullable=false, updatable=false)
	private Date startDate;
	private Date endDate;
	
	@JoinColumn(name="person_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private DbPerson person;
	
private Long views;
	
	@ManyToMany
	@JoinTable(name = "news_visibility",
	    joinColumns = @JoinColumn(name="job", referencedColumnName="id"),
	    inverseJoinColumns = @JoinColumn(name="person_type", referencedColumnName="id"))
	Set<DbPersonType> visibleToPersonTypes;
	
	
	// Mark this as not insertable so the default database value will be used.
	@Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean active;
    
	// Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean abuse;
    
    @OneToMany(mappedBy="job", fetch=FetchType.EAGER)
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
	public Integer getIndustry() {
		return industry;
	}
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
	public int getjobType() {
		return jobType;
	}
	public void setjobType(int jobType) {
		this.jobType = jobType;
	}
	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getendDate() {
		return endDate;
	}
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getpostedbyPersonId() {
		return postedByPersonId;
	}
	public void setpostedbyPersonId(int postedbypersonId) {
		this.postedByPersonId = postedbypersonId;
	}
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
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
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DbJob other = (DbJob) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (hours == null) {
			if (other.hours != null)
				return false;
		} else if (!hours.equals(other.hours))
			return false;
		if (jobType == null) {
			if (other.jobType != null)
				return false;
		} else if (!jobType.equals(other.jobType))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (postedByPersonId == null) {
			if (other.postedByPersonId != null)
				return false;
		} else if (!postedByPersonId.equals(other.postedByPersonId))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("{ Job: ")
				.append("{ Id: ").append(id)
				.append(", title; ").append(title)
				.append(" }\n")
				.append(" }\n")
				.toString();
	}

	
}
