package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbPersonType;

public interface PersonTypeService {
	/**
	 * Returns all {@link DbPersonType} instances.
	 */
	Set<DbPersonType> findAll();

}
