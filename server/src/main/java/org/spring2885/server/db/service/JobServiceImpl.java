package org.spring2885.server.db.service;

import java.util.Iterator;
import java.util.List;

import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbPerson;
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
	JobServiceImpl(JobRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DbJob findById(long id) {
		return repository.findOne(id);
	}
	
    @Override
    public Iterable<DbJob> findAll(DbPerson me, boolean all) {
        if (all) {
            return repository.findAll();
        }
        return repository.findAllByActiveAndAbuse(true, false);
                
    }
    
	@Override
    public Iterable<DbJob> findAll(DbPerson me, boolean all, String q) {
        return Iterables.filter(findAll(me, all), new SearchFilter(q));
    }
	
	@Override
	public boolean delete(long id) {
		DbJob n = findById(id);
		if(n != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}
	
	@Override
	public DbJob save(DbJob jobs) {
		return repository.save(jobs);
	}
	
	@Override
    public Iterable<DbJob> findAll(DbPerson me, boolean all, List<SearchCriteria> criterias) {
        if (criterias.isEmpty()) {
            return findAll(me, all);
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbJob> specs = new SearchCriteriaSpecification<>(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
        }
        return repository.findAll(specs);
    }
	    
	static class SearchFilter implements Predicate<DbJob> {
	    private final String q;
	    public SearchFilter(String q) { this.q = q; }
	    
        @Override
        public boolean apply(DbJob p) {
            if (p.getTitle() != null && p.getTitle().contains(q)) {
                return true;
            }
            // TODO(jen): Finish this.
            return false;
        }
	}
}
