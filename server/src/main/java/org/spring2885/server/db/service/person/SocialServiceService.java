package org.spring2885.server.db.service.person;

import java.util.Iterator;
import java.util.Set;

import org.spring2885.server.db.model.DbSocialService;

public interface SocialServiceService {
	
	DbSocialService findById(String q);
	
	//Iterable<DbSocialService> findAll(String q);
	/**
	 * Returns all {@link DbSocialService} instances.
	 */
	Set<DbSocialService> findAll();
	
	boolean delete(String q);
	
	DbSocialService save(DbSocialService ss);
}
