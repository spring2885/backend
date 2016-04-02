package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.search.SearchCriteria;

public interface NewsService {

	/**
	 * Returns a {@link DbNews} by the primary key or {@code null} if none exist.
	 */
	DbNews findById(long id);
	
	/**
     * Returns all {@code DbNews} instances with search string {code q}.
     */
    Iterable<DbNews> findAll(DbPerson me, boolean all, String q);

    /**
     * Returns all non-deleted and non-abuse {@code DbNews} instances.
     */
    Iterable<DbNews> findAll(DbPerson me, boolean all);
    
	/**
     * Returns all {@code DbNews} instances with search string {code q}.
     */
    Iterable<DbNews> findAll(DbPerson me, boolean all, List<SearchCriteria> criterias);
	
	/**
	 * Deletes a news post by primary key.
	 * 
	 * @return true on success, false otherwise.
	 */
	boolean delete(long id);
	
	/**
	 * Saves (Inserts) the {@code news} into the database.
	 */
	DbNews save(DbNews news);
	
}
