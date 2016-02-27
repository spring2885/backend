package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-02-18T11:27:17.171-08:00")
public class Job  {
  
  private Long id = null;
  private String title = null;
  private Integer industry = null;
  private String location = null;
  private String description = null;
  private Integer jobType = null;
  private Date startDate = null;
  private Date endDate = null;
  private Integer postedbypersonId = null;
  private Integer hours = null;
  

  
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
  public Integer getIndustry() {
    return industry;
  }
  public void setIndustry(Integer industry) {
    this.industry = industry;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("location")
  public String getLocation() {
    return title;
  }
  public void setLocation(String location) {
    this.location = location;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("description")
  public String description() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("jobType")
  public Integer getjobType() {
    return jobType;
  }
  public void setjobType(Integer jobType) {
    this.jobType = jobType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("startDate")
  public Date getstartDate() {
    return startDate;
  }
  public void setstartDate(Date startDate) {
    this.startDate = startDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("endDate")
  public Date getendDate() {
    return endDate;
  }
  public void setendDate(Date endDate) {
    this.endDate = endDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("postedbypersonId")
  public Integer getpostedbypersonId() {
    return postedbypersonId;
  }
  public void setpostedbypersonId(Integer postedbypersonId) {
    this.postedbypersonId = postedbypersonId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("hours")
  public Integer getHours() {
    return hours;
  }
  public void setHours(Integer hours) {
    this.hours = hours;
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
        Objects.equals(title, job.title) &&
        Objects.equals(industry, job.industry) &&
        Objects.equals(location, job.location) &&
        Objects.equals(description, job.description) &&
        Objects.equals(jobType, job.jobType) &&
        Objects.equals(startDate, job.startDate) &&
        Objects.equals(endDate, job.endDate) &&
        Objects.equals(postedbypersonId, job.postedbypersonId) &&
        Objects.equals(hours, job.hours);
        
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, industry, location, description, jobType, startDate, endDate, postedbypersonId,hours);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  industry: ").append(industry).append("\n");
    sb.append("  location: ").append(location).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  jobType: ").append(jobType).append("\n");
    sb.append("  startDate: ").append(startDate).append("\n");
    sb.append("  endDate: ").append(endDate).append("\n");
    sb.append("  postedbypersonId: ").append(postedbypersonId).append("\n");
    sb.append("  hours: ").append(hours).append("\n");
    
    sb.append("}\n");
    return sb.toString();
  }
}
