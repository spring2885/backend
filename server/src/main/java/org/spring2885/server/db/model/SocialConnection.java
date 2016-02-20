package org.spring2885.server.db.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SocialConnection {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private DbPerson person;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="social_service_id")
	private SocialService socialService;
	
	private String url;

	
	public DbPerson getPerson() {
		return person;
	}

	public void setPerson(DbPerson person) {
		this.person = person;
	}

	public SocialService getSocialService() {
		return socialService;
	}

	public void setSocialService(SocialService socialService) {
		this.socialService = socialService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
