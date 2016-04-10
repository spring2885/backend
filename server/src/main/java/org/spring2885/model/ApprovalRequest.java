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
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-10T16:03:39.070+02:00")
public class ApprovalRequest  {
  
  private String id = null;
  private Boolean active = null;
  private String approvalType = null;
  private String itemType = null;
  private Long itemId = null;
  private String notes = null;
  private Date on = null;
  private Person by = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("active")
  public Boolean getActive() {
    return active;
  }
  public void setActive(Boolean active) {
    this.active = active;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("approval_type")
  public String getApprovalType() {
    return approvalType;
  }
  public void setApprovalType(String approvalType) {
    this.approvalType = approvalType;
  }

  
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
  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("on")
  public Date getOn() {
    return on;
  }
  public void setOn(Date on) {
    this.on = on;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("by")
  public Person getBy() {
    return by;
  }
  public void setBy(Person by) {
    this.by = by;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApprovalRequest approvalRequest = (ApprovalRequest) o;
    return Objects.equals(id, approvalRequest.id) &&
        Objects.equals(active, approvalRequest.active) &&
        Objects.equals(approvalType, approvalRequest.approvalType) &&
        Objects.equals(itemType, approvalRequest.itemType) &&
        Objects.equals(itemId, approvalRequest.itemId) &&
        Objects.equals(notes, approvalRequest.notes) &&
        Objects.equals(on, approvalRequest.on) &&
        Objects.equals(by, approvalRequest.by);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, active, approvalType, itemType, itemId, notes, on, by);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApprovalRequest {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  active: ").append(active).append("\n");
    sb.append("  approvalType: ").append(approvalType).append("\n");
    sb.append("  itemType: ").append(itemType).append("\n");
    sb.append("  itemId: ").append(itemId).append("\n");
    sb.append("  notes: ").append(notes).append("\n");
    sb.append("  on: ").append(on).append("\n");
    sb.append("  by: ").append(by).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
