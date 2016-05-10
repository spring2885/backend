package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbTemplate;

public interface TemplateService {
    Set<DbTemplate> findAll();
    DbTemplate findById(String id);
}
