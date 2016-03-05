package org.spring2885.server.db.service.person;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.search.SearchCriteria;

public interface PersonService {
	/**
	 * Returns a {@link DbPerson} by the primary key or {@code null} if none exist.
	 */
	DbPerson findById(long id);
	
    /**
     * Returns all {@code DbPerson} instances.
     */
    Iterable<DbPerson> findAll();
    
    /**
     * Returns all {@code DbPerson} instances with search string {code q}.
     */
    Iterable<DbPerson> findAll(String q);
    
    /**
     * Returns all {@code DbPerson} instances with search string {code q}.
     */
    Iterable<DbPerson> findAll(List<SearchCriteria> criterias);
    
	/**
	 * Returns all {@code DbPerson} instances with email address of {@code email}
	 * or an empty {@link Iterator} if none exist.
	 */
	List<DbPerson> findByEmail(String email);
	
	/** Returns all by grad year. */
	List<DbPerson> findByGraduationYear(Integer year);
	
	/**
	 * Deletes a user by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);
	
	/** Returns {@code true} if a user exists for this email address. */
	boolean existsByEmail(String email);

	/** Saves (Inserts) the {@code person} into the database. */
	DbPerson save(DbPerson person);
}
