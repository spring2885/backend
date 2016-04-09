package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbNews;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<DbJob, Long>, JpaSpecificationExecutor<DbJob> {

    List<DbJob> findByTitle(String title);

    List<DbJob> findByDescription(String description);
    
    Iterable<DbJob> findAllByActiveAndAbuse(boolean active,boolean abuse);
    
}