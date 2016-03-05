package org.spring2885.server.db.service.person;

import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@Component("socialServiceService")
@Transactional(readOnly=true)
public class SocialServiceServiceImpl implements SocialServiceService {
	private final SocialServiceRepository repository;
	
	@Autowired
	public SocialServiceServiceImpl(SocialServiceRepository repository) {
		this.repository = repository;
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

}
