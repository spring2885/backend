package org.spring2885.server.db.service.person;

import java.util.List;

import org.spring2885.server.db.model.DbPersonType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface PersonTypeRepository extends CrudRepository<DbPersonType, Long> {

	Iterable<DbPersonType> findAll(Specification<DbPersonType> specs);

	List<DbPersonType> findByName(String name);

}
