package org.spring2885.server.db.service.person;

import java.util.Iterator;
import java.util.List;

import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("personService")
@Transactional
public class PersonServiceImpl implements PersonService {
	private final PersonRepository repository;
	
	@Autowired
	public PersonServiceImpl(PersonRepository repository) {
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
	public DbPerson findByEmail(String email) {
	    List<DbPerson> candidates = repository.findByEmail(email);
	    // Since the email column has a unique constraint on it, there can
	    // only be 0 or 1 DbPerson's returned.
	    if (candidates.isEmpty()) {
	        return null;
	    }
	    return Iterables.getOnlyElement(candidates);
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
		return findByEmail(email) != null;
	}

	@Override
	public DbPerson save(DbPerson person) {
		return repository.save(person);
	}

    @Override
    public List<DbPerson> findByGraduationYear(Integer year) {
        return repository.findByGraduationYear(year);
    }

    @Override
    public Iterable<DbPerson> findAll(List<SearchCriteria> criterias) {
        if (criterias.isEmpty()) {
            return findAll();
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbPerson> specs = new DbPersonSpecification(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new DbPersonSpecification(iter.next()));
        }
        return repository.findAll(specs);
    }

}
