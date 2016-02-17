package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<DbPerson, Integer>{
	List<DbPerson> findByEmail(String email);
}
