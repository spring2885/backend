package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbNewsComment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NewsCommentRepository 
    extends CrudRepository<DbNewsComment, Long>, 
            JpaSpecificationExecutor<DbNewsComment> {
}
