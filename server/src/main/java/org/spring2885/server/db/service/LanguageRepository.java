package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbLanguage;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<DbLanguage, String> {
}
