package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;

public interface LanguageTypeService {
	Set<DbLanguage> findAll();
	
	/** Gets the default person type (student). */
	DbLanguage defaultType();
}
