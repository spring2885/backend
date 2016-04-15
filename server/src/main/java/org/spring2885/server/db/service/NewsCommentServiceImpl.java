package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbNewsComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("newsCommentService")
@Transactional
public class NewsCommentServiceImpl implements NewsCommentService {
    private final NewsCommentRepository repository;

    @Autowired
    public NewsCommentServiceImpl(NewsCommentRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public DbNewsComment findById(long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean delete(long id) {
        DbNewsComment n = findById(id);
        if (n != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public DbNewsComment save(DbNewsComment news) {
        return repository.save(news);
    }

}
