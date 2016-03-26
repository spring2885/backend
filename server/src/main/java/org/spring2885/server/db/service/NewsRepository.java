package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface NewsRepository extends 
        CrudRepository<DbNews, Long>, 
        JpaSpecificationExecutor<DbNews>{

	List<DbNews> findByTitle(String Title);
	
	Iterable<DbNews> findAllByActiveAndAbuse(char active, char abuse);
	
}

