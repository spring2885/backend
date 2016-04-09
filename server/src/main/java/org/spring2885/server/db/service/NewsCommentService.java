package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbNewsComment;

public interface NewsCommentService {
    /**
     * Returns a {@link DbNewsComment} by the primary key or {@code null} if none exist.
     */
    DbNewsComment findById(long id);
    
    /**
     * Deletes a news comentby primary key.
     * 
     * @return true on success, false otherwise.
     */
    boolean delete(long id);
    
    /**
     * Saves (Inserts) the {@code news_comment} into the database.
     */
    DbNewsComment save(DbNewsComment news);

}
