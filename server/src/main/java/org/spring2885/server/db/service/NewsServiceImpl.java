package org.spring2885.server.db.service;

import java.util.Iterator;
import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Component("newsService")
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
	public Iterable<DbNews> findAllAdmin() {
		return repository.findAll();
	}
	
    @Override
    public Iterable<DbNews> findAll() {
        return repository.findAllByActiveAndAbuse(true, false);
    }
    
	@Override
    public Iterable<DbNews> findAll(String q) {
        // TODO(rob): Push this filter into the DB.
        return Iterables.filter(findAll(), new Predicate<DbNews>() {
            @Override
            public boolean apply(DbNews p) {
                if (p.getTitle() != null && p.getTitle().contains(q)) {
                    return true;
                }
                return false;
            }
        });
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
	
	@Override
    public Iterable<DbNews> findAll(List<SearchCriteria> criterias) {
        if (criterias.isEmpty()) {
            return findAll();
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbNews> specs = new SearchCriteriaSpecification<>(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
        }
        return repository.findAll(specs);
    }
}
