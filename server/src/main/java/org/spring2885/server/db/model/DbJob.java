package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Version;



@Entity
@EntityListeners(AuditingEntityListener.class)
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
	private Date startDate;
	private Date endDate;
	
	// Mark this as not insertable so the default database value will be used.
	@Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean active;
	
	// Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
	private Boolean abuse;
	
	@CreatedDate
	private Date creationTime;
	
	@LastModifiedDate
    private Date modificationTime;
    
	@Version
	private long version;

	
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
	
<<<<<<< HEAD
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
   
=======
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	public Date getCreationTime(){
		return creationTime;
	}
	
	public void setModificationTime(Date modificationTime){
		this.modificationTime = modificationTime;
	}
	public Date getModificationTime(){
		return modificationTime;
	}
	
	public void setVersion(long version){
		this.version = version;
	}
	public long getVersion(){
		return version;
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
		return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "DbJob [id=" + id + ", title=" + title + ", industry=" + industry + ", location=" + location
				+ ", description=" + description + ", jobType=" + jobType + ", startDate=" + startDate + ", endDate="
				+ endDate + ", postedByPersonId=" + postedByPersonId + ", hours=" + hours + ", creationTime="
				+ creationTime + ", modificationTime=" + modificationTime + ", version=" + version + "]";
	}


	
}
