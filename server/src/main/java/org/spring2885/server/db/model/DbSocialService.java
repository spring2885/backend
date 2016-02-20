package org.spring2885.server.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="social_service")
public class DbSocialService {
	
	@Id
	private String id;
	
	private String url;
	
	public String getName() {
		return id;
	}
	public void setName(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
