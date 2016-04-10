package org.spring2885.server.db.service.person;

import java.util.List;
import java.util.Set;

import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.search.SearchCriteria;

public interface PersonTypeService {
	
	/**
	 * Returns all {@link DbPersonType} instances.
	 */
	Set<DbPersonType> findAll();
	 
    /**
     * Returns all {@code DbPersonType} instances with search string {code q}.
     */
	Iterable<DbPersonType> findAll(String q);
    
    /**
     * Returns all {@code DbPersonType} instances with search string {code q}.
     */
	Iterable<DbPersonType> findAll(List<SearchCriteria> criterias);
	
    /** Gets the default person type (student). */
    DbPersonType defaultType();
    
    /** Gets the default person type (student). */
    DbPersonType facultyType();
    
	/**
	 * Returns a {@link DbPersonType} by the primary key or {@code null} if none exist.
	 */
	DbPersonType findById(long id);
	
	/**
	 * Returns all {@code DbPersonType} instances with name address of {@code name}
	 * or an empty {@link Iterator} if none exist.
	 */
	DbPersonType findByName(String name);
   
	/**
	 * Deletes a personType by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);

	/** Saves (Inserts) the {@code person} into the database. */
	DbPersonType save(DbPersonType personType);
	
	/**
	 * Returns {@code true} if personType exists for this name.
	 */
	boolean existsByName(String name);
	
}
