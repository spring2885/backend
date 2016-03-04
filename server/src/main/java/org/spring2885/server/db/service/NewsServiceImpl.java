package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("NewsService")
@Transactional
public class NewsServiceImpl implements NewsService {
	private final NewsRepository repository;
	
	@Autowired
	NewsServiceImpl(NewsRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DbNews findById(long id)
	{
		return repository.findOne(id);
	}
	
	@Override
	public Iterable<DbNews> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<DbNews> findByTitle(String title)
	{
		return repository.findByTitle(title);
	}
	
	@Override
	public boolean delete(long id)
	{
		DbNews n = findById(id);
		if(n != null) {
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
	public DbNews save(DbNews news) {
		return repository.save(news);
	}
}
