package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;
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
	public DbPerson findById(int id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<DbPerson> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<DbPerson> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public boolean delete(int id) {
		DbPerson p = findById(id);
		if (p != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByEmail(String email) {
		return findByEmail(email).size() != 0;
	}

}
