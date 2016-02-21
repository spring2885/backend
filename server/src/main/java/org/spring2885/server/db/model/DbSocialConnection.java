package org.spring2885.server.db.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="social_connection")
public class DbSocialConnection {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private DbPerson person;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="social_service_id")
	private DbSocialService socialService;
	
	private String url;

	
	public DbPerson getPerson() {
		return person;
	}

	public void setPerson(DbPerson person) {
		this.person = person;
	}

	public DbSocialService getSocialService() {
		return socialService;
	}

	public void setSocialService(DbSocialService socialService) {
		this.socialService = socialService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(person, socialService);
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) { return true; }
	    if (o == null) { return false; }
	    if (!(o instanceof DbSocialConnection)) {
	        return false;
	    }
	    DbSocialConnection that = (DbSocialConnection) o;
        return Objects.equals(this.person, that.person)
                && Objects.equals(this.socialService, that.socialService);
	}

}