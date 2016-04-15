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
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-14T22:09:20.994-07:00")
public class ApprovalRequest  {
  
  private String id = null;
  private Boolean active = null;
  private String approvalType = null;
  private String itemType = null;
  private Long itemId = null;
  private String itemUrl = null;
  private String flaggedNotes = null;
  private Date flaggedOn = null;
  private Person flaggedBy = null;
  private Boolean approved = null;
  private String verdictNotes = null;
  private Date verdictOn = null;
  private Person verdictBy = null;

  
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
  @JsonProperty("flagged_notes")
  public String getFlaggedNotes() {
    return flaggedNotes;
  }
  public void setFlaggedNotes(String flaggedNotes) {
    this.flaggedNotes = flaggedNotes;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("flagged_on")
  public Date getFlaggedOn() {
    return flaggedOn;
  }
  public void setFlaggedOn(Date flaggedOn) {
    this.flaggedOn = flaggedOn;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("flagged_by")
  public Person getFlaggedBy() {
    return flaggedBy;
  }
  public void setFlaggedBy(Person flaggedBy) {
    this.flaggedBy = flaggedBy;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("approved")
  public Boolean getApproved() {
    return approved;
  }
  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("verdict_notes")
  public String getVerdictNotes() {
    return verdictNotes;
  }
  public void setVerdictNotes(String verdictNotes) {
    this.verdictNotes = verdictNotes;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("verdict_on")
  public Date getVerdictOn() {
    return verdictOn;
  }
  public void setVerdictOn(Date verdictOn) {
    this.verdictOn = verdictOn;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("verdict_by")
  public Person getVerdictBy() {
    return verdictBy;
  }
  public void setVerdictBy(Person verdictBy) {
    this.verdictBy = verdictBy;
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
        Objects.equals(itemUrl, approvalRequest.itemUrl) &&
        Objects.equals(flaggedNotes, approvalRequest.flaggedNotes) &&
        Objects.equals(flaggedOn, approvalRequest.flaggedOn) &&
        Objects.equals(flaggedBy, approvalRequest.flaggedBy) &&
        Objects.equals(approved, approvalRequest.approved) &&
        Objects.equals(verdictNotes, approvalRequest.verdictNotes) &&
        Objects.equals(verdictOn, approvalRequest.verdictOn) &&
        Objects.equals(verdictBy, approvalRequest.verdictBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, active, approvalType, itemType, itemId, itemUrl, flaggedNotes, flaggedOn, flaggedBy, approved, verdictNotes, verdictOn, verdictBy);
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
    sb.append("  itemUrl: ").append(itemUrl).append("\n");
    sb.append("  flaggedNotes: ").append(flaggedNotes).append("\n");
    sb.append("  flaggedOn: ").append(flaggedOn).append("\n");
    sb.append("  flaggedBy: ").append(flaggedBy).append("\n");
    sb.append("  approved: ").append(approved).append("\n");
    sb.append("  verdictNotes: ").append(verdictNotes).append("\n");
    sb.append("  verdictOn: ").append(verdictOn).append("\n");
    sb.append("  verdictBy: ").append(verdictBy).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
