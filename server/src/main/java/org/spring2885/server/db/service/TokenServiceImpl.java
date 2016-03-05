package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {
	private final TokenRepository repository;
	
	@Autowired
	TokenServiceImpl(TokenRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DbToken findById(String uuid) {
		return repository.findOne(uuid);
	}

	@Override
	public Iterable<DbToken> findAll() {
		return repository.findAll();
	}

	@Override
	public List<DbToken> findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	@Override
	public boolean delete(String uuid){
		DbToken token = findById(uuid);
		if (token != null){
			repository.delete(uuid);
			return true;
		}
		return false;
	}
	
	public boolean deleteByEmail(String email) {
	    repository.deleteByEmail(email);
		return true;
	}

	@Override
	public boolean existsByEmail(String email) {
		return findByEmail(email).size() != 0;
	}

	@Override
	public DbToken save(DbToken token) {
		return repository.save(token);
	}
}
