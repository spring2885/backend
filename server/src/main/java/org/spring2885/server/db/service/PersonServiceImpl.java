package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("personService")
@Transactional
public class PersonServiceImpl implements PersonService {
	private final PersonRepository repository;
	
	@Autowired
	PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Person findById(int id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<Person> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<Person> findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
