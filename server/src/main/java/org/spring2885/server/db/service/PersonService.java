package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.Person;

public interface PersonService {
	Person findById(int id); 
	Iterable<Person> findAll();
	List<Person> findByEmail(String email);
}
