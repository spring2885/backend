package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbJobType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JobTypeRepository extends 
	CrudRepository<DbJobType, Long>,
	JpaSpecificationExecutor<DbJobType> {

}
