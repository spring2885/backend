package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;

public interface PersonService {
	/**
	 * Returns a {@link DbPerson} by the primary key or {@code null} if none exist.
	 */
	DbPerson findById(int id);
	
	/**
	 * Returns all {@code DbPerson} instances.
	 */
	Iterable<DbPerson> findAll();
	
	/**
	 * Returns all {@code DbPerson} instances with email address of {@code email}
	 * or an empty {@link Iterator} if none exist.
	 */
	List<DbPerson> findByEmail(String email);
	
	/**
	 * Deletes a user by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(int id);
	
	/** Returns {@code true} if a user exists for this email address. */
	boolean existsByEmail(String email);

	/** Saves (Inserts) the {@code person} into the database. */
	DbPerson save(DbPerson person);
}
