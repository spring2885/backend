package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.DbSocialService;
import org.springframework.data.repository.CrudRepository;

public interface PersonTypeRepository extends CrudRepository<DbPersonType, Long> {

}
