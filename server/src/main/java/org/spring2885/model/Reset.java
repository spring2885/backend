package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-10T17:14:37.756+02:00")
public class Reset  {
  
  private String email = null;
  private String token = null;
  private String newPassword = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("token")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("new_password")
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reset reset = (Reset) o;
    return Objects.equals(email, reset.email) &&
        Objects.equals(token, reset.token) &&
        Objects.equals(newPassword, reset.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, token, newPassword);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reset {\n");
    
    sb.append("  email: ").append(email).append("\n");
    sb.append("  token: ").append(token).append("\n");
    sb.append("  newPassword: ").append(newPassword).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
