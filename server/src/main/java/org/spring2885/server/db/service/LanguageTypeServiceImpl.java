package org.spring2885.server.db.service;


import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;

import com.google.common.collect.Iterables;

public class LanguageTypeServiceImpl implements LanguageTypeService {
	private final LangRepository repository;
	LanguageTypeServiceImpl(LangRepository repository) {
		this.repository = repository;
	}
	public DbLanguage defaultType() {
		return repository.findOne(Long.valueOf(0));
	}
	@Override
	public Set<DbLanguage> findAll() {
		Iterable<DbLanguage> services = repository.findAll();
		Set<DbLanguage> result = new HashSet<>();
		if (services != null) {
			Iterables.addAll(result, services);
		}
		return result;
	}
	}