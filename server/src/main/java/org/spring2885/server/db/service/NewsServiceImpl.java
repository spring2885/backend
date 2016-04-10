package org.spring2885.server.db.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
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
	public DbNews findById(long id) {
		return repository.findOne(id);
	}
	
    @Override
    public Iterable<DbNews> findAll(DbPerson me, boolean all) {
        if (all) {
            return repository.findAll();
        }
        return Iterables.filter(
                repository.findAllByActiveAndAbuse(true, false),
                new NewsVisibilityFilter(me.getType()));
    }
    
	@Override
    public Iterable<DbNews> findAll(DbPerson me, boolean all, String q) {
	    return Iterables.filter(findAll(me, all), new SearchFilter(q));
    }
	
	@Override
	public boolean delete(long id) {
		DbNews n = findById(id);
		if(n != null) {
			repository.delete(id);
			return true;
		}
		return false;
	}
	
	@Override
	public DbNews save(DbNews news) {
		return repository.save(news);
	}
	
	@Override
    public Iterable<DbNews> findAll(DbPerson me, boolean all, List<SearchCriteria> criterias) {
        if (criterias.isEmpty()) {
            return findAll(me, all);
        }
        Iterator<SearchCriteria> iter = criterias.iterator();
        Specification<DbNews> specs = new SearchCriteriaSpecification<>(iter.next());
        while (iter.hasNext()) {
            specs = Specifications.where(specs).and(new SearchCriteriaSpecification<>(iter.next()));
        }
        return Iterables.filter(repository.findAll(specs),
                new NewsVisibilityFilter(me.getType()));
    }
	
	static class NewsVisibilityFilter implements Predicate<DbNews> {
	    private final DbPersonType visibleType;
	    public NewsVisibilityFilter(DbPersonType visibleType) {
	        this.visibleType = visibleType;
	    }
	    
        @Override
        public boolean apply(DbNews news) {
            Set<DbPersonType> newsPersonTypes = news.getVisibleToPersonTypes();
            if (newsPersonTypes == null) {
                // TODO(rob): Should we fail open and let it through if it's missing
                // from the visibility table?
                return false;
            }
            if (visibleType == null) {
                return false;
            }
            
            long visibleTypeId = visibleType.getId();
            for (DbPersonType t : newsPersonTypes) {
                if (t.getId().longValue() == visibleTypeId) { return true; }
            }
            return false;
        }
	    
	}
	
	static class SearchFilter implements Predicate<DbNews> {
	    private final String q;
	    public SearchFilter(String q) { this.q = q; }
	    
        @Override
        public boolean apply(DbNews p) {
            if (p.getTitle() != null && p.getTitle().contains(q)) {
                return true;
            }
            return false;
        }
	}
}
