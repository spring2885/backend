package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbNews;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface NewsRepository extends 
        CrudRepository<DbNews, Long>, 
        JpaSpecificationExecutor<DbNews>{
	
	Iterable<DbNews> findAllByActiveAndAbuse(boolean active, boolean abuse);
}

