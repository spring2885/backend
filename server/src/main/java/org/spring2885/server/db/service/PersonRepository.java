package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	List<Person> findByEmail(String email);
}
