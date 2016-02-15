//Log: 2-12-16 Version: 0.0.1
package org.spring2885.hibernate;

import javax.persistence.*;
import javax.persistence.Entity;

import java.io.Serializable;
import java.sql.*;


@Entity
@Table(name = "Person")
public class Person implements Serializable {
	@Id @GeneratedValue
	
	@Column(name = "id")
	private int id;
	
	@Column(name = "person_name")
	private String personName;

	@Column(name = "student_id")
	private int studentId;

	@Column(name = "title")
	private String title;

	@Column(name = "about_me")
	private String aboutMe;

	@Column(name = "resume_url")
	private String resumeURL;

	@Column(name = "person_image_url")
	private String personImageUrl;
	
	@Column(name = "person_email")
	private String personEmail;
	
	@Column(name = "person_phone")
	private String personPhone;
	
	@Column(name = "person_occupation")
	private String personOccupation;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "person_birthdate")
	private Date personBirthdate;
	
	@Column(name = "person_type")
	private int personType;
	
	@Column(name = "last_logon")
	private Date lastLogon;
	
	@Column(name = "password_sha")
	private String passwordSha;
	
	@Column(name = "password_salt")
	private String passwordSalt;
	
	//constructor
	public Person(){
	}
	
	//getter and setter for id
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	//getter and setter for personName
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	//getter and setter for studentId
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	//getter and setter for title
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	//getter and setter for aboutMe
	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	//getter and setter for resumeURL
	public String getResumeURL() {
		return resumeURL;
	}

	public void setResumeURL(String resumeURL) {
		this.resumeURL = resumeURL;
	}

	//getter and setter for personImageUrl
	public String getPersonImageUrl() {
		return personImageUrl;
	}

	public void setPersonImageUrl(String personImageUrl) {
		this.personImageUrl = personImageUrl;
	}

	//getter and setter for personEmail
	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	
	//getter and setter for personPhone
	public String getPersonPhone() {
		return personPhone;
	}

	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}

	//getter and setter for personOccupation
	public String getPersonOccupation() {
		return personOccupation;
	}

	public void setPersonOccupation(String personOccupation) {
		this.personOccupation = personOccupation;
	}

	//getter and setter companyName
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	//getter and setter personBirthdate
	public Date getPersonBirthdate() {
		return personBirthdate;
	}

	public void setPersonBirthdate(Date personBirthdate) {
		this.personBirthdate = personBirthdate;
	}

	//getter and setter personType
	public int getPersonType() {
		return personType;
	}

	public void setPersonType(int personType) {
		this.personType = personType;
	}

	//getter and setter lastLogon
	public Date getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}

	//getter and setter passwordSha
	public String getPasswordSha() {
		return passwordSha;
	}

	public void setPasswordSha(String passwordSha) {
		this.passwordSha = passwordSha;
	}

	//getter and setter passwordSalt
	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
}
