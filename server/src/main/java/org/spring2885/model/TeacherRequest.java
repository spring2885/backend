package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-10T16:24:22.862+02:00")
public class TeacherRequest  {
  
  private String notes = null;

  
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
    TeacherRequest teacherRequest = (TeacherRequest) o;
    return Objects.equals(notes, teacherRequest.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notes);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeacherRequest {\n");
    
    sb.append("  notes: ").append(notes).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
