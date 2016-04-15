package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.spring2885.model.Person;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-14T20:02:00.301-07:00")
public class Job  {
  
  private Long id = null;
  private String description = null;
  private String company = null;
  private Long jobType = null;
  private String title = null;
  private Long industry = null;
  private String location = null;
  private Date startDate = null;
  private Date endDate = null;
  private Long postedByPersonId = null;
  private Long hours = null;
  private Person postedBy = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("company")
  public String getCompany() {
    return company;
  }
  public void setCompany(String company) {
    this.company = company;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("job_type")
  public Long getJobType() {
    return jobType;
  }
  public void setJobType(Long jobType) {
    this.jobType = jobType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("industry")
  public Long getIndustry() {
    return industry;
  }
  public void setIndustry(Long industry) {
    this.industry = industry;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("start_date")
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("end_date")
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("posted_by_person_id")
  public Long getPostedByPersonId() {
    return postedByPersonId;
  }
  public void setPostedByPersonId(Long postedByPersonId) {
    this.postedByPersonId = postedByPersonId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("hours")
  public Long getHours() {
    return hours;
  }
  public void setHours(Long hours) {
    this.hours = hours;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("posted_by")
  public Person getPostedBy() {
    return postedBy;
  }
  public void setPostedBy(Person postedBy) {
    this.postedBy = postedBy;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Job job = (Job) o;
    return Objects.equals(id, job.id) &&
        Objects.equals(description, job.description) &&
        Objects.equals(company, job.company) &&
        Objects.equals(jobType, job.jobType) &&
        Objects.equals(title, job.title) &&
        Objects.equals(industry, job.industry) &&
        Objects.equals(location, job.location) &&
        Objects.equals(startDate, job.startDate) &&
        Objects.equals(endDate, job.endDate) &&
        Objects.equals(postedByPersonId, job.postedByPersonId) &&
        Objects.equals(hours, job.hours) &&
        Objects.equals(postedBy, job.postedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, company, jobType, title, industry, location, startDate, endDate, postedByPersonId, hours, postedBy);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Job {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  company: ").append(company).append("\n");
    sb.append("  jobType: ").append(jobType).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  industry: ").append(industry).append("\n");
    sb.append("  location: ").append(location).append("\n");
    sb.append("  startDate: ").append(startDate).append("\n");
    sb.append("  endDate: ").append(endDate).append("\n");
    sb.append("  postedByPersonId: ").append(postedByPersonId).append("\n");
    sb.append("  hours: ").append(hours).append("\n");
    sb.append("  postedBy: ").append(postedBy).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
