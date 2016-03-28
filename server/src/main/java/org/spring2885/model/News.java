package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.util.Date;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-03-26T17:33:48.856-07:00")
public class News  {
  
  private Long id = null;
  private String title = null;
  private String description = null;
  private Date posted = null;
  private Date expired = null;
  private String postedBy = null;
  private Long views = null;
  private List<String> visibleTo = new ArrayList<String>();

  
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
  @JsonProperty("posted")
  public Date getPosted() {
    return posted;
  }
  public void setPosted(Date posted) {
    this.posted = posted;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("expired")
  public Date getExpired() {
    return expired;
  }
  public void setExpired(Date expired) {
    this.expired = expired;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("posted_by")
  public String getPostedBy() {
    return postedBy;
  }
  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("views")
  public Long getViews() {
    return views;
  }
  public void setViews(Long views) {
    this.views = views;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("visible_to")
  public List<String> getVisibleTo() {
    return visibleTo;
  }
  public void setVisibleTo(List<String> visibleTo) {
    this.visibleTo = visibleTo;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    News news = (News) o;
    return Objects.equals(id, news.id) &&
        Objects.equals(title, news.title) &&
        Objects.equals(description, news.description) &&
        Objects.equals(posted, news.posted) &&
        Objects.equals(expired, news.expired) &&
        Objects.equals(postedBy, news.postedBy) &&
        Objects.equals(views, news.views) &&
        Objects.equals(visibleTo, news.visibleTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, posted, expired, postedBy, views, visibleTo);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class News {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  posted: ").append(posted).append("\n");
    sb.append("  expired: ").append(expired).append("\n");
    sb.append("  postedBy: ").append(postedBy).append("\n");
    sb.append("  views: ").append(views).append("\n");
    sb.append("  visibleTo: ").append(visibleTo).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
