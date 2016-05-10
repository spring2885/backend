package org.spring2885.server.db.service;

import java.util.Collections;
import java.util.Set;

import org.spring2885.server.db.model.DbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Component("templateService")
@Transactional(readOnly=true)
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository repository;
    
    @Autowired
    public TemplateServiceImpl(TemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<DbTemplate> findAll() {
        Iterable<DbTemplate> iter = repository.findAll();
        if (iter == null) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(iter);
    }

    @Override
    public DbTemplate findById(String id) {
        return repository.findOne(id);
    }

}
