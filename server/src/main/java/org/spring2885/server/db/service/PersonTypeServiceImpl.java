package org.spring2885.server.db.service;

import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbPersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@Component("personTypeService")
@Transactional(readOnly=true)
public class PersonTypeServiceImpl implements PersonTypeService {
	private final PersonTypeRepository repository;
	
	@Autowired
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
		return repository.findOne(Long.valueOf(0));
	}

}
