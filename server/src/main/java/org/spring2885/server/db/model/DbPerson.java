package org.spring2885.server.db.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Cacheable
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="person")
public class DbPerson {
    private static final String DEFAULT_IMAGE = "/src/assets/images/Profilepic.png";
    
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
	private String occupation;
	private String companyName;
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
	
    @Version
	private Long version;

	@CreatedDate
	private java.util.Date creationTime;
		
	@LastModifiedDate
	private java.util.Date modificationTime;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedBy
	private String modifiedBy;
	

	@Column(nullable=false, insertable=false, columnDefinition="TINYINT", length = 1)
	private Boolean active;


    @OneToMany(orphanRemoval = true, mappedBy="person")
    private Set<DbRole> roles = new HashSet<>();
    
    public DbPerson() {}
    public DbPerson(long id, String email, String name) {
        this.id = Long.valueOf(id);
        this.email = email;
        this.name = name;
    }

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
	    if (imageUrl == null) {
	        // Return a default image if none exists.
	        return DEFAULT_IMAGE;
	    }
		return imageUrl;
	}
	public void setImageURL(String imageURL) {
	    if (DEFAULT_IMAGE.equals(imageURL)) {
	        // Don't store the default image.
	        this.imageUrl = null;
	    } else {
	        this.imageUrl = imageURL;
	    }
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public java.util.Date getCreationTime(){
		return creationTime;
	}
	
	public java.util.Date getModificationTime(){
		return modificationTime;
	}
	
	public Long getVersion(){
		return version;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public String getLastModifiedBy(){
		return modifiedBy;
	}

	public Boolean isActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}


	public void addRoleForTesting(String rolename) {
	    roles.add(new DbRole(this, rolename));
	}
	
    public Set<DbRole> roles() {
        if (roles.isEmpty()) {
            roles.add(new DbRole(this, "USER"));
        }
        return roles;
    }
    
    public boolean isAdmin() {
        return roles.stream().anyMatch((t) -> t.rolename().equalsIgnoreCase("ADMIN"));
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
				.append(" }")
				.append(" }")
				.toString();
	}
}
