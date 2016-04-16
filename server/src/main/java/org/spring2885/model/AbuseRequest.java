package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-14T22:09:20.994-07:00")
public class AbuseRequest  {
  
  private String itemType = null;
  private Long itemId = null;
  private String itemUrl = null;
  private String notes = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("item_type")
  public String getItemType() {
    return itemType;
  }
  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("item_id")
  public Long getItemId() {
    return itemId;
  }
  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("item_url")
  public String getItemUrl() {
    return itemUrl;
  }
  public void setItemUrl(String itemUrl) {
    this.itemUrl = itemUrl;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbuseRequest abuseRequest = (AbuseRequest) o;
    return Objects.equals(itemType, abuseRequest.itemType) &&
        Objects.equals(itemId, abuseRequest.itemId) &&
        Objects.equals(itemUrl, abuseRequest.itemUrl) &&
        Objects.equals(notes, abuseRequest.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemType, itemId, itemUrl, notes);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AbuseRequest {\n");
    
    sb.append("  itemType: ").append(itemType).append("\n");
    sb.append("  itemId: ").append(itemId).append("\n");
    sb.append("  itemUrl: ").append(itemUrl).append("\n");
    sb.append("  notes: ").append(notes).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
