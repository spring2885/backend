package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbLanguage;

public interface languageService {
	Set<DbLanguage> findAll();
}