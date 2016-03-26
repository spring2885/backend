package org.spring2885.server.db.service.person;

import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.DbSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("socialServiceService")
@Transactional(readOnly=true)
public class SocialServiceServiceImpl implements SocialServiceService {
	private final SocialServiceRepository repository;
	
	@Autowired
	public SocialServiceServiceImpl(SocialServiceRepository repository) {
		this.repository = repository;
	}

	/*@Override
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
    }*/

	@Override
	public DbSocialService findById(String q)
	{
		return repository.findOne(q);
	}
	
	@Override
	public Set<DbSocialService> findAll() {
		Iterable<DbSocialService> services = repository.findAll();
		Set<DbSocialService> result = new HashSet<>();
		if (services != null) {
			Iterables.addAll(result, services);
		}
		return result;
	}
	
	@Override
	public DbSocialService save(DbSocialService ss) {
		return repository.save(ss);
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
}
