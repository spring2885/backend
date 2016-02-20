package org.spring2885.server.db.service;

import java.util.Set;

import org.spring2885.server.db.model.DbSocialService;

public interface SocialServiceService {
	/**
	 * Returns all {@link DbSocialService} instances.
	 */
	Set<DbSocialService> findAll();
}
