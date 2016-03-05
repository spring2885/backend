package org.spring2885.server.db.service;

import java.util.List;
import org.spring2885.server.db.model.DbNews;

public interface NewsService {

	/**
	 * Returns a {@link DbNews} by the primary key or {@code null} if none exist.
	 */
	DbNews findById(long id);
	
	/**
	 * Returns all {@code DbNews} instances.
	 */
	Iterable<DbNews> findAll();
	
	/**
	 * Returns all {@code DbNews} instances with news title of {@code title}
	 * or an empty {@link Iterator} if none exist.
	 */
	
	List<DbNews> findByTitle(String title);
	
	/**
	 * Deletes a news post by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);
	
	/** Returns {@code true} if a user exists for a news post. */
	boolean existsByTitle(String title);
	
	/** Saves (Inserts) the {@code news} into the database. */
	DbNews save(DbNews news);
	
}
