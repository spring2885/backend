package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="token")
public class DbToken {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String email;
	private String uuid;
	private String uuidStatus;
	private Date dateCreate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getUuidStatus() {
		return uuidStatus;
	}
	public void setUuidStatus(String uuidStatus) {
		this.uuidStatus = uuidStatus;
	}
	public Date getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("{ Token: ")
				.append("{ Id: ").append(id)
				.append(", email; ").append(email)
				.append(", uuidStatus; ").append(uuidStatus)
				.append(" }\n")
				.append(" }\n")
				.toString();
	}
}
