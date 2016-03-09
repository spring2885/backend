package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbJob;

public interface JobService {
	/**
	 * Returns a {@link DbJob} by the primary key or {@code null} if none exist.
	 */
	DbJob findById(long id);
	
	/**
	 * Returns all {@code DbJob} instances.
	 */
	Iterable<DbJob> findAll();
	
	/**
	 * Returns all {@code DbJob} instances with email address of {@code email}
	 * or an empty {@link Iterator} if none exist.
	 */
	List<DbJob> findByTitle(String title);
	
	/**
	 * Deletes a user by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);
	
	/** Returns {@code true} if a user exists for this email address. */
	boolean existsByTitle(String title);

	/** Saves (Inserts) the {@code person} into the database. */
	DbJob save(DbJob job);
}
