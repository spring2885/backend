package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-02-16T21:38:18.078-08:00")
public class Person  {
  
  private Long id = null;
  private String username = null;
  private Object profilePicture = null;
  private String firstname = null;
  private String lastname = null;
  private String email = null;
  private String phone = null;
  private Object birthdate = null;
  private String occupation = null;
  private Object lastLoginDate = null;
  private String variety = null;

  
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
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("profile picture")
  public Object getProfilePicture() {
    return profilePicture;
  }
  public void setProfilePicture(Object profilePicture) {
    this.profilePicture = profilePicture;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("firstname")
  public String getFirstname() {
    return firstname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("lastname")
  public String getLastname() {
    return lastname;
  }
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  
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
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("birthdate")
  public Object getBirthdate() {
    return birthdate;
  }
  public void setBirthdate(Object birthdate) {
    this.birthdate = birthdate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("occupation")
  public String getOccupation() {
    return occupation;
  }
  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("last_login_date")
  public Object getLastLoginDate() {
    return lastLoginDate;
  }
  public void setLastLoginDate(Object lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("variety")
  public String getVariety() {
    return variety;
  }
  public void setVariety(String variety) {
    this.variety = variety;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(id, person.id) &&
        Objects.equals(username, person.username) &&
        Objects.equals(profilePicture, person.profilePicture) &&
        Objects.equals(firstname, person.firstname) &&
        Objects.equals(lastname, person.lastname) &&
        Objects.equals(email, person.email) &&
        Objects.equals(phone, person.phone) &&
        Objects.equals(birthdate, person.birthdate) &&
        Objects.equals(occupation, person.occupation) &&
        Objects.equals(lastLoginDate, person.lastLoginDate) &&
        Objects.equals(variety, person.variety);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, profilePicture, firstname, lastname, email, phone, birthdate, occupation, lastLoginDate, variety);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  username: ").append(username).append("\n");
    sb.append("  profilePicture: ").append(profilePicture).append("\n");
    sb.append("  firstname: ").append(firstname).append("\n");
    sb.append("  lastname: ").append(lastname).append("\n");
    sb.append("  email: ").append(email).append("\n");
    sb.append("  phone: ").append(phone).append("\n");
    sb.append("  birthdate: ").append(birthdate).append("\n");
    sb.append("  occupation: ").append(occupation).append("\n");
    sb.append("  lastLoginDate: ").append(lastLoginDate).append("\n");
    sb.append("  variety: ").append(variety).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
