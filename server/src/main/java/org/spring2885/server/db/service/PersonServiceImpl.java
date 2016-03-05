package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("personService")
@Transactional
public class PersonServiceImpl implements PersonService {
	private final PersonRepository repository;
	
	@Autowired
	PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
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
    public Iterable<DbPerson> findAll(String q) {
        // TODO(rob): Push this filter into the DB.
        return Iterables.filter(findAll(), new Predicate<DbPerson>() {
            @Override
            public boolean apply(DbPerson p) {
                if (p.getCompanyName() != null && p.getCompanyName().contains(q)) {
                    return true;
                }
                if (p.getName() != null && p.getName().contains(q)) {
                    return true;
                }
                if (p.getOccupation() != null && p.getOccupation().contains(q)) {
                    return true;
                } 
                if (p.getTitle() != null && p.getTitle().contains(q)) {
                    return true;
                }
                return false;
            }
        });
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
		return repository.save(person);
	}

    @Override
    public List<DbPerson> findByGraduationYear(Integer year) {
        return repository.findByGraduationYear(year);
    }

}
