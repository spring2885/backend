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
	
	@Override
	public int hashCode() {
	    return id.hashCode();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (!(o instanceof DbSocialService)) {
            return false;
        }
        DbSocialService that = (DbSocialService) o;
        return this.id.equals(that.id);
    }

}
