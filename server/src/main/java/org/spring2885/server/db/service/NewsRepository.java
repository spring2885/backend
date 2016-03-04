package org.spring2885.server.db.service;

import java.util.List;

import org.spring2885.server.db.model.DbNews;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<DbNews, Long>{
	List<DbNews> findByTitle(String title);

}
