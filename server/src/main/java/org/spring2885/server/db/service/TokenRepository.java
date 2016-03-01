package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<DbToken, Long> {
	List<DbToken> findByEmail(String email);
	List<DbToken> deleteByEmail(String email);
}
