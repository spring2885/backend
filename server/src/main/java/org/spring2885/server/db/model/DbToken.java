package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="token")
public class DbToken {
	@Id
    private String uuid;
	private String email;
	private Date dateCreated;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("{ Token: ")
				.append("{ uuid: ").append(uuid)
				.append(", email; ").append(email)
				.append(", date_created: ").append(dateCreated)
				.append(" }\n")
				.append(" }\n")
				.toString();
	}
}
