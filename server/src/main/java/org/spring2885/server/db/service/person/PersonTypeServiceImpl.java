package org.spring2885.server.db.service.person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("personTypeService")
@Transactional(readOnly=true)
public class PersonTypeServiceImpl implements PersonTypeService {
	private final PersonTypeRepository repository;
    private static final Long STUDENT_TYPE_ID = 1L;
    private static final Long FACULTY_TYPE_ID = 3L;
	
	@Autowired
    public
	PersonTypeServiceImpl(PersonTypeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Set<DbPersonType> findAll() {
		Iterable<DbPersonType> services = repository.findAll();
		Set<DbPersonType> result = new HashSet<>();
		if (services != null) {
			Iterables.addAll(result, services);
		}
		return result;
	}

	@Override
	public DbPersonType defaultType() {
		return repository.findOne(STUDENT_TYPE_ID);
	}

    @Override
    public DbPersonType facultyType() {
        return repository.findOne(FACULTY_TYPE_ID);
    }

    @Override
	public DbPersonType findById(long id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<DbPersonType> findAll(String q) {
		return Iterables.filter(findAll(), new Predicate<DbPersonType>() {
            @Override
            public boolean apply(DbPersonType p) {
                if (p.getId() != null && p.getId().toString().contains(q)) {
                    return true;
                }
                if (p.getName() != null && p.getName().contains(q)) {
                    return true;
                }
                return false;
            }
        });
	}
	
	@Override
	public Iterable<DbPersonType> findAll(List<SearchCriteria> criterias) {
		if (criterias.isEmpty()) {
            return findAll();
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbPersonType> specs = new SearchCriteriaSpecification<>(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
        }
        return repository.findAll(specs);
	}
	
	@Override
	public boolean delete(long id) {
		DbPersonType pT = findById(id);
		if (pT != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}

	@Override
	public DbPersonType save(DbPersonType personType) {
		return repository.save(personType);
	}
	
	@Override
	public boolean existsByName(String name){
		return findByName(name) != null;
	}

	@Override
	public DbPersonType findByName(String name) {
		List<DbPersonType> candidates = repository.findByName(name);
		if (candidates.isEmpty()){
			return null;
		}
		return Iterables.getOnlyElement(candidates);
	}

}
