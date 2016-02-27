package org.spring2885.server.db.service;

import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbJobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@Component("jobTypeService")
@Transactional(readOnly=true)
public class JobTypeServiceImpl implements JobTypeService {
	private final JobTypeRepository repository;
	
	@Autowired
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

}
