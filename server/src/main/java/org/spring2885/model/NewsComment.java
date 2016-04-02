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
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-02T12:08:13.393-07:00")
public class NewsComment  {
  
  private Long id = null;
  private Long newsId = null;
  private String text = null;
  private Date posted = null;
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
  @JsonProperty("news_id")
  public Long getNewsId() {
    return newsId;
  }
  public void setNewsId(Long newsId) {
    this.newsId = newsId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("text")
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
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
    NewsComment newsComment = (NewsComment) o;
    return Objects.equals(id, newsComment.id) &&
        Objects.equals(newsId, newsComment.newsId) &&
        Objects.equals(text, newsComment.text) &&
        Objects.equals(posted, newsComment.posted) &&
        Objects.equals(postedBy, newsComment.postedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newsId, text, posted, postedBy);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewsComment {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  newsId: ").append(newsId).append("\n");
    sb.append("  text: ").append(text).append("\n");
    sb.append("  posted: ").append(posted).append("\n");
    sb.append("  postedBy: ").append(postedBy).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
