package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbJob;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<DbJob, Long>, JpaSpecificationExecutor<DbJob> {

    Iterable<DbJob> findAllByActiveAndAbuse(boolean active, boolean abuse);
}