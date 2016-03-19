package org.spring2885.server.db.service;

import java.util.List;
import org.spring2885.server.db.model.DbSocialService;
import org.spring2885.server.db.service.search.SearchCriteria;

public interface SocialServiceService {
	
	DbSocialService findById(String id);
	
	Iterable<DbSocialService> findAll(String q);
	
	Iterable<DbSocialService> findAll();
	
	boolean delete(String id);
	
	DbSocialService save(DbSocialService social);
	
}
