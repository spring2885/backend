package org.spring2885.server.db.service.person;

import java.util.List;

import org.spring2885.server.db.model.DbPerson;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends 
        CrudRepository<DbPerson, Long>, 
        JpaSpecificationExecutor<DbPerson>{

	List<DbPerson> findByEmail(String email);
	
	List<DbPerson> findByGraduationYear(Integer year);
	
	Iterable<DbPerson> findAllByActive(boolean active);
}
