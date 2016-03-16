package org.spring2885.server.db.service;


import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@Component("LanguageTypeService")
@Transactional(readOnly=true)
public class LanguageTypeServiceImpl implements LanguageTypeService {
	private final LangRepository repository;
	LanguageTypeServiceImpl(LangRepository repository) {
		this.repository = repository;
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