package org.spring2885.server.db.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;


@Component("jobTypeService")
public class JobTypeServiceImpl implements JobTypeService {
	private final JobTypeRepository repository;
	
	@Autowired
    public
	JobTypeServiceImpl(JobTypeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Set<DbJobType> findAll() {
		Iterable<DbJobType> services = repository.findAll();
		Set<DbJobType> result = new HashSet<>();
		if (services != null) {
			Iterables.addAll(result, services);
		}
		return result;
	}

	@Override
	public DbJobType defaultType() {
		return repository.findOne(Long.valueOf(0));
	}

	@Override
	public DbJobType findById(long id) {
		return repository.findOne(id);
	}

	@Override
    public DbJobType save(DbJobType job) {
        return repository.save(job);
    }

	@Override
    public boolean delete(long id) {
        DbJobType p = findById(id);
        if (p != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

	@Override
	public Iterable<DbJobType> findAll(String q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<DbJobType> findAll(List<SearchCriteria> criterias) {
		if (criterias.isEmpty()) {
			return findAll();
		}	
		Iterator<SearchCriteria> iter = criterias.iterator();
		Specification<DbJobType> specs = new SearchCriteriaSpecification<>(iter.next());
		while(iter.hasNext()) {
			specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
		}
		return repository.findAll(specs);
	}
	@Override
	public boolean existsByName(String name) {
		return findByName(name) != null;
	}

	@Override
	public DbJobType findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
