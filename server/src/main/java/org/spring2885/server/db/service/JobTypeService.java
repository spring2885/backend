package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbJobType;

public interface JobTypeService {
	/**
	 * Returns all {@link DbPersonType} instances.
	 */
	Set<DbJobType> findAll();
	
	/** Gets the default person type (student). */
	DbJobType defaultType();
}
