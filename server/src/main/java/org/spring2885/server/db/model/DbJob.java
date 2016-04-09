package org.spring2885.server.db.model;

import java.sql.Date;

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
	
	private Integer jobType;

	private Date startDate;
	
	private Date endDate;
	
	private Integer postedByPersonId;
	
	private Integer hours;
	
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
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (hours == null) {
			if (other.hours != null)
				return false;
		} else if (!hours.equals(other.hours))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (jobType == null) {
			if (other.jobType != null)
				return false;
		} else if (!jobType.equals(other.jobType))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (modificationTime == null) {
			if (other.modificationTime != null)
				return false;
		} else if (!modificationTime.equals(other.modificationTime))
			return false;
		if (postedByPersonId == null) {
			if (other.postedByPersonId != null)
				return false;
		} else if (!postedByPersonId.equals(other.postedByPersonId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DbJob [id=" + id + ", title=" + title + ", industry=" + industry + ", location=" + location
				+ ", description=" + description + ", jobType=" + jobType + ", startDate=" + startDate + ", endDate="
				+ endDate + ", postedByPersonId=" + postedByPersonId + ", hours=" + hours + ", creationTime="
				+ creationTime + ", modificationTime=" + modificationTime + ", version=" + version + "]";
	}


	
}
