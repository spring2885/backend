package org.spring2885.server.db.service;

import java.util.Iterator;
import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;


@Component("socialServiceService")
@Transactional
public class SocialServiceImpl implements SocialServiceService {
	private final SocialServiceRepository repository;
	
	@Autowired
	SocialServiceImpl(SocialServiceRepository repository){
		this.repository = repository;
	}
	
	@Override
	public DbSocialService findById(String id)
	{
		return repository.findOne(id);
	}
	
	@Override
	public Iterable<DbSocialService> findAll() {
		return repository.findAll();
	}
	
	@Override
    public Iterable<DbSocialService> findAll(String q) {
        // TODO(rob): Push this filter into the DB.
        return Iterables.filter(findAll(), new Predicate<DbSocialService>() {
            @Override
            public boolean apply(DbSocialService p) {
                if (p.getName() != null && p.getName().contains(q)) {
                    return true;
                }
                return false;
            }
        });
    }
	
	@Override
	public boolean delete(String id)
	{
		DbSocialService n = findById(id);
		if(n != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}
	
	
	@Override
	public DbSocialService save(DbSocialService ss) {
		return repository.save(ss);
	}

}
