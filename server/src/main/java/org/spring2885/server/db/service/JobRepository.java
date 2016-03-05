package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbJob;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<DbJob, Long>{ 
	List<DbJob> findByTitle(String title);
}
