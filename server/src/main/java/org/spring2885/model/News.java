package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-02-28T17:50:45.537-08:00")
public class News  {
  
  private Long id = null;
  private String newsTitle = null;
  private String newsDescription = null;
  private Object newsPosted = null;
  private Object newsExpired = null;
  private Long newsPersonId = null;
  private Long newsViews = null;

  
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
  @JsonProperty("news_title")
  public String getNewsTitle() {
    return newsTitle;
  }
  public void setNewsTitle(String newsTitle) {
    this.newsTitle = newsTitle;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("news_description")
  public String getNewsDescription() {
    return newsDescription;
  }
  public void setNewsDescription(String newsDescription) {
    this.newsDescription = newsDescription;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("news_posted")
  public Object getNewsPosted() {
    return newsPosted;
  }
  public void setNewsPosted(Object newsPosted) {
    this.newsPosted = newsPosted;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("news_expired")
  public Object getNewsExpired() {
    return newsExpired;
  }
  public void setNewsExpired(Object newsExpired) {
    this.newsExpired = newsExpired;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("news_person_id")
  public Long getNewsPersonId() {
    return newsPersonId;
  }
  public void setNewsPersonId(Long newsPersonId) {
    this.newsPersonId = newsPersonId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("news_views")
  public Long getNewsViews() {
    return newsViews;
  }
  public void setNewsViews(Long newsViews) {
    this.newsViews = newsViews;
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
        Objects.equals(newsTitle, news.newsTitle) &&
        Objects.equals(newsDescription, news.newsDescription) &&
        Objects.equals(newsPosted, news.newsPosted) &&
        Objects.equals(newsExpired, news.newsExpired) &&
        Objects.equals(newsPersonId, news.newsPersonId) &&
        Objects.equals(newsViews, news.newsViews);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newsTitle, newsDescription, newsPosted, newsExpired, newsPersonId, newsViews);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class News {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  newsTitle: ").append(newsTitle).append("\n");
    sb.append("  newsDescription: ").append(newsDescription).append("\n");
    sb.append("  newsPosted: ").append(newsPosted).append("\n");
    sb.append("  newsExpired: ").append(newsExpired).append("\n");
    sb.append("  newsPersonId: ").append(newsPersonId).append("\n");
    sb.append("  newsViews: ").append(newsViews).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
