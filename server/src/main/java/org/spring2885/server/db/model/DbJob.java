package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	private Integer jobType;

	private Date startDate;
	
	private Date endDate;
	
	private Integer postedbyPersonId;
	
	private Integer hours;
	
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
		return postedbyPersonId;
	}
	public void setpostedbyPersonId(int postedbypersonId) {
		this.postedbyPersonId = postedbypersonId;
	}
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
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
		if (postedbyPersonId == null) {
			if (other.postedbyPersonId != null)
				return false;
		} else if (!postedbyPersonId.equals(other.postedbyPersonId))
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
