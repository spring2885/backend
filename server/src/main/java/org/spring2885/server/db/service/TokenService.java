package org.spring2885.server.db.service;

import java.util.List;
import org.spring2885.server.db.model.DbToken;

public interface TokenService {
	/**
	 * Returns a {@link DbToken} by id
	 */
	DbToken findById(long id);
	
	/**
	 * Returns all {@code DbToken} instances
	 */
	Iterable<DbToken> findAll();
	
	/**
	 * Returns all {@code DbToken} instances with email address of {@code email} 
	 */
	List<DbToken> findByEmail(String email);
	
	/**
	 * Deletes by id
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);
	
	/**
	 * Returns {@code true} if an id exists for this email address
	 */
	boolean existsByEmail(String email);
	
	/**
	 * Saves (inserts) the {@code DbToken} into the database.
	 */
	DbToken save(DbToken token);
	
	/**
	 * delete tokens identified with matching email
	 */
	boolean deleteByEmail(String email);
}
