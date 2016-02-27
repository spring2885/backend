package org.spring2885.server.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Job_Type")
public class DbJobType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	public DbJobType() {
	}
	
	public DbJobType(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
