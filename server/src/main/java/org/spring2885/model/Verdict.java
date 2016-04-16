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
public class Verdict  {
  
  private String id = null;
  private Boolean approved = null;
  private String verdictNotes = null;

  
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

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Verdict verdict = (Verdict) o;
    return Objects.equals(id, verdict.id) &&
        Objects.equals(approved, verdict.approved) &&
        Objects.equals(verdictNotes, verdict.verdictNotes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, approved, verdictNotes);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Verdict {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  approved: ").append(approved).append("\n");
    sb.append("  verdictNotes: ").append(verdictNotes).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
