package org.spring2885.server.db.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class DbPerson {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Integer studentId;
	private String title;
	private String aboutMe;
	private String resumeUrl;
	private String imageUrl;
	private String email;
	private String phone;
	private String occupation;
	private String companyName;
	private Date birthdate;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private DbPersonType type;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lang")
    private DbLanguage lang;
	private Date lastLogon;
	private String password;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="person")
	private Set<DbSocialConnection> socialConnections = new HashSet<>();
	private String degreeMajor;
	private String degreeMinor;
	private Integer graduationYear;
	private String degreeType;
	private String facultyDepartment;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getResumeURL() {
		return resumeUrl;
	}
	public void setResumeURL(String resumeURL) {
		this.resumeUrl = resumeURL;
	}
	public String getImageURL() {
		return imageUrl;
	}
	public void setImageURL(String imageURL) {
		this.imageUrl = imageURL;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public DbPersonType getType() {
		return type;
	}
	public void setType(DbPersonType type) {
		this.type = type;
	}
	public Date getLastLogon() {
		return lastLogon;
	}
	public DbLanguage getLanguage() {
	    return lang;
	}
	public void setLanguage(DbLanguage lang) {
	    this.lang = lang;
	}
	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<DbSocialConnection> socialConnections() {
		return socialConnections;
	}
	
	public String getDegreeMajor() {
		return degreeMajor;
	}

	public void setDegreeMajor(String degreeMajor) {
		this.degreeMajor = degreeMajor;
	}

	public String getDegreeMinor() {
		return degreeMinor;
	}

	public void setDegreeMinor(String degreeMinor) {
		this.degreeMinor = degreeMinor;
	}

	public Integer getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(Integer graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	public String getFacultyDepartment() {
		return facultyDepartment;
	}

	public void setFacultyDepartment(String facultyDepartment) {
		this.facultyDepartment = facultyDepartment;
	}

	// Since we won't have this object outside without the ID, we're ok
	// See https://developer.jboss.org/wiki/EqualsandHashCode
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DbPerson)) {
			return false;
		}
		DbPerson that = (DbPerson) obj;
		return this.id.equals(that.id);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("{ Person: ")
				.append("{ Id: ").append(id)
				.append(", name; ").append(name)
				.append(" }\n")
				.append(" }\n")
				.toString();
	}
}
