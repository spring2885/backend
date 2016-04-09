package org.spring2885.server.db.service;

import java.util.Iterator;
import java.util.List;

import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("jobService")
@Transactional
public class JobServiceImpl implements JobService {
	private final JobRepository repository;
	
	@Autowired
	public JobServiceImpl(JobRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DbJob findById(long id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<DbJob> findAll() {
		return repository.findAll();
	}
	
	@Override
    public Iterable<DbJob> findAll(String q) {
        // TODO(rob): Push this filter into the DB.
        return Iterables.filter(findAll(), new Predicate<DbJob>() {
            @Override
            public boolean apply(DbJob p) {
                if (p.getTitle() != null && p.getTitle().contains(q)) {
                    return true;
                }
                if (p.getDescription() != null && p.getDescription().contains(q)) {
                    return true;
                }
                if (p.getLocation() != null && p.getLocation().contains(q)) {
                    return true;
                }
                return false;
            }
        });
    }
    
     @Override
    public DbJob findByTitle(String title) {
        List<DbJob> candidates = repository.findByTitle(title);
        // Since the email column has a unique constraint on it, there can
        // only be 0 or 1 DbPerson's returned.
        if (candidates.isEmpty()) {
            return null;
        }
        return Iterables.getOnlyElement(candidates);
    }

    @Override
    public boolean delete(long id) {
        DbJob p = findById(id);
        if (p != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByTitle(String title) {
        return findByTitle(title) != null;
    }

    @Override
    public DbJob save(DbJob job) {
        return repository.save(job);
    }

    @Override
    public List<DbJob> findByDescription(String description) {
        return repository.findByDescription(description);
    }

    @Override
    public Iterable<DbJob> findAll(List<SearchCriteria> criterias) {
        if (criterias.isEmpty()) {
            return findAll();
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbJob> specs = new SearchCriteriaSpecification<>(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
        }
        return repository.findAll(specs);
    }

	

}