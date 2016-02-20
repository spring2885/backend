package org.spring2885.server.db.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.spring2885.server.db.model.DbPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("personService")
@Transactional
public class PersonServiceImpl implements PersonService {
	private final PersonRepository repository;
    private final JpaContext jpaContext;
    private final EntityManager em;
	
	@Autowired
	PersonServiceImpl(PersonRepository repository, JpaContext context) {
		this.repository = repository;
		this.jpaContext = context;
		this.em = context.getEntityManagerByManagedType(DbPerson.class);
	}
	
	@Override
	public DbPerson findById(long id) {
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
	public boolean delete(long id) {
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

	@Override
	public DbPerson save(DbPerson person) {
		try {
			//em.getTransaction().begin();
			//em.createNativeQuery("DELETE FROM Social_Connections WHERE person_id = " + person.getId());
			return repository.save(person);
		} finally {
			//em.getTransaction().commit();
		}
	}

}
