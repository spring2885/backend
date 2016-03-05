package org.spring2885.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.util.Date;
import org.spring2885.model.SocialConnection;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-03-05T14:08:56.619-08:00")
public class Person  {
  
  private Long id = null;
  private String name = null;
  private Integer studentId = null;
  private String title = null;
  private String aboutMe = null;
  private String resumeUrl = null;
  private String imageUrl = null;
  private String email = null;
  private String phone = null;
  private String occupation = null;
  private String companyName = null;
  private Date birthdate = null;
  private String variety = null;
  private Date lastLoginDate = null;
  private List<SocialConnection> socialConnections = new ArrayList<SocialConnection>();
  private String lang = null;
  private String degreeMajor = null;
  private String degreeMinor = null;
  private Integer graduationYear = null;
  private String degreeType = null;
  private String facultyDepartment = null;

  
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
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("student_id")
  public Integer getStudentId() {
    return studentId;
  }
  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
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
  @JsonProperty("about_me")
  public String getAboutMe() {
    return aboutMe;
  }
  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("resume_url")
  public String getResumeUrl() {
    return resumeUrl;
  }
  public void setResumeUrl(String resumeUrl) {
    this.resumeUrl = resumeUrl;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("image_url")
  public String getImageUrl() {
    return imageUrl;
  }
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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
  @JsonProperty("company_name")
  public String getCompanyName() {
    return companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("birthdate")
  public Date getBirthdate() {
    return birthdate;
  }
  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
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

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("last_login_date")
  public Date getLastLoginDate() {
    return lastLoginDate;
  }
  public void setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("social_connections")
  public List<SocialConnection> getSocialConnections() {
    return socialConnections;
  }
  public void setSocialConnections(List<SocialConnection> socialConnections) {
    this.socialConnections = socialConnections;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("lang")
  public String getLang() {
    return lang;
  }
  public void setLang(String lang) {
    this.lang = lang;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("degree_major")
  public String getDegreeMajor() {
    return degreeMajor;
  }
  public void setDegreeMajor(String degreeMajor) {
    this.degreeMajor = degreeMajor;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("degree_minor")
  public String getDegreeMinor() {
    return degreeMinor;
  }
  public void setDegreeMinor(String degreeMinor) {
    this.degreeMinor = degreeMinor;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("graduation_year")
  public Integer getGraduationYear() {
    return graduationYear;
  }
  public void setGraduationYear(Integer graduationYear) {
    this.graduationYear = graduationYear;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("degree_type")
  public String getDegreeType() {
    return degreeType;
  }
  public void setDegreeType(String degreeType) {
    this.degreeType = degreeType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("faculty_department")
  public String getFacultyDepartment() {
    return facultyDepartment;
  }
  public void setFacultyDepartment(String facultyDepartment) {
    this.facultyDepartment = facultyDepartment;
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
        Objects.equals(name, person.name) &&
        Objects.equals(studentId, person.studentId) &&
        Objects.equals(title, person.title) &&
        Objects.equals(aboutMe, person.aboutMe) &&
        Objects.equals(resumeUrl, person.resumeUrl) &&
        Objects.equals(imageUrl, person.imageUrl) &&
        Objects.equals(email, person.email) &&
        Objects.equals(phone, person.phone) &&
        Objects.equals(occupation, person.occupation) &&
        Objects.equals(companyName, person.companyName) &&
        Objects.equals(birthdate, person.birthdate) &&
        Objects.equals(variety, person.variety) &&
        Objects.equals(lastLoginDate, person.lastLoginDate) &&
        Objects.equals(socialConnections, person.socialConnections) &&
        Objects.equals(lang, person.lang) &&
        Objects.equals(degreeMajor, person.degreeMajor) &&
        Objects.equals(degreeMinor, person.degreeMinor) &&
        Objects.equals(graduationYear, person.graduationYear) &&
        Objects.equals(degreeType, person.degreeType) &&
        Objects.equals(facultyDepartment, person.facultyDepartment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, studentId, title, aboutMe, resumeUrl, imageUrl, email, phone, occupation, companyName, birthdate, variety, lastLoginDate, socialConnections, lang, degreeMajor, degreeMinor, graduationYear, degreeType, facultyDepartment);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  studentId: ").append(studentId).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  aboutMe: ").append(aboutMe).append("\n");
    sb.append("  resumeUrl: ").append(resumeUrl).append("\n");
    sb.append("  imageUrl: ").append(imageUrl).append("\n");
    sb.append("  email: ").append(email).append("\n");
    sb.append("  phone: ").append(phone).append("\n");
    sb.append("  occupation: ").append(occupation).append("\n");
    sb.append("  companyName: ").append(companyName).append("\n");
    sb.append("  birthdate: ").append(birthdate).append("\n");
    sb.append("  variety: ").append(variety).append("\n");
    sb.append("  lastLoginDate: ").append(lastLoginDate).append("\n");
    sb.append("  socialConnections: ").append(socialConnections).append("\n");
    sb.append("  lang: ").append(lang).append("\n");
    sb.append("  degreeMajor: ").append(degreeMajor).append("\n");
    sb.append("  degreeMinor: ").append(degreeMinor).append("\n");
    sb.append("  graduationYear: ").append(graduationYear).append("\n");
    sb.append("  degreeType: ").append(degreeType).append("\n");
    sb.append("  facultyDepartment: ").append(facultyDepartment).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
