package org.spring2885.server.db.service;

import java.util.HashSet;
import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@Component("languageService")
@Transactional(readOnly=true)
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository repository;
    
    @Autowired
    LanguageServiceImpl(LanguageRepository repository) {
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

    @Override
    public DbLanguage defaultLanguage() {
        return repository.findOne("en");
    }

	@Override
	public DbLanguage findByCode(String code) {
		return repository.findOne(code);
	}

	@Override
	public DbLanguage save(DbLanguage Language) {
			return repository.save(Language);
	}

}
