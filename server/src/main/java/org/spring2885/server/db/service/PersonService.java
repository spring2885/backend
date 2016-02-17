package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;

public interface PersonService {
	DbPerson findById(int id); 
	Iterable<DbPerson> findAll();
	List<DbPerson> findByEmail(String email);
}
