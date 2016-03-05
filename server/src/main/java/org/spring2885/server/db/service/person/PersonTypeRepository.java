package org.spring2885.server.db.service.person;

import org.spring2885.server.db.model.DbPersonType;
import org.springframework.data.repository.CrudRepository;

public interface PersonTypeRepository extends CrudRepository<DbPersonType, Long> {

}
