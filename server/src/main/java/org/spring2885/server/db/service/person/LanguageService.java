package org.spring2885.server.db.service.person;

import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;

public interface LanguageService {
    Set<DbLanguage> findAll();
    DbLanguage defaultLanguage();
}