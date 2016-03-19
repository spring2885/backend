package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbSocialService;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SocialServiceRepository extends 
	CrudRepository<DbSocialService, String>,
	JpaSpecificationExecutor<DbSocialService>{
		
		List<DbSocialService> findById(String id);
	}


