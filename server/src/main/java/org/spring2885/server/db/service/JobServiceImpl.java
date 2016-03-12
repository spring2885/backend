package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("jobService")
@Transactional
public class JobServiceImpl implements JobService {
	private final JobRepository repository;
	
	@Autowired
	JobServiceImpl(JobRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DbJob findById(long id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<DbJob> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<DbJob> findByTitle(String title) {
		return repository.findByTitle(title);
	}

	@Override
	public boolean delete(long id) {
		DbJob p = findById(id);
		if (p != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByTitle(String title) {
		return findByTitle(title).size() != 0;
	}

	@Override
	public DbJob save(DbJob job) {
		return repository.save(job);
	}

}