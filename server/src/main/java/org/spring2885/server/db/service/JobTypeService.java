package org.spring2885.server.db.service;

import java.util.List;
import java.util.Set;

import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.service.search.SearchCriteria;

public interface JobTypeService {
	
	/**
	 * Returns all {@link DbJobType} instances.
	 */
	Set<DbJobType> findAll();
    
    /**
     * Returns all {@code DbJobType} instances with search string {code q}.
     */
	Iterable<DbJobType> findAll(List<SearchCriteria> criterias);
	
	/** Gets the default job type (student). */
	DbJobType defaultType();
	
	/**
	 * Returns a {@link DbJobType} by the primary key or {@code null} if none exist.
	 */
	DbJobType findById(long id);
   
	/**
	 * Deletes a jobType by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);

	/** Saves (Inserts) the {@code person} into the database. */
	DbJobType save(DbJobType jobType);

	
}
