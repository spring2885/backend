package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbSocialService;
import org.springframework.data.repository.CrudRepository;

public interface SocialServiceRepository extends CrudRepository<DbSocialService, String> {
}
