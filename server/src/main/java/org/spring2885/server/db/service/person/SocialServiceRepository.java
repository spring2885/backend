package org.spring2885.server.db.service.person;

import org.spring2885.server.db.model.DbSocialService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface SocialServiceRepository extends CrudRepository<DbSocialService, String> {
	
	Iterable<DbSocialService> findAll(Specification<DbSocialService> spec);
}

