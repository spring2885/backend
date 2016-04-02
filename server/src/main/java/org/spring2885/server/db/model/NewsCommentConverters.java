package org.spring2885.server.db.model;

import org.spring2885.model.NewsComment;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

@Component
public class NewsCommentConverters {
    @Autowired
    private PersonService personService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;

    public class NewsCommentFromDbToJson implements Function<DbNewsComment, NewsComment> {
        
        @Override
        public NewsComment apply(DbNewsComment db) {
            NewsComment n = new NewsComment();
            n.setId(db.getId());
            n.setNewsId(db.getNews().getId());
            n.setPosted(db.getCommentTimestamp());
            n.setText(db.getCommentText());

            DbPerson person = db.getPerson();
            n.setPostedBy(personFromDbToJson.apply(person));

            return n;
        }
    }
    
    public class NewsCommentJsonToDbConverter implements Function<NewsComment, DbNewsComment> {
        private Supplier<DbNewsComment> dbSupplier = Suppliers.ofInstance(new DbNewsComment());
        
        NewsCommentJsonToDbConverter() {
        }
        
        public NewsCommentJsonToDbConverter withDbNewsComment(DbNewsComment db) {
            this.dbSupplier = Suppliers.ofInstance(db);
            return this;
        }
        
        @Override
        public DbNewsComment apply(NewsComment p) {
            DbNewsComment db = dbSupplier.get();
            db.setId(p.getId());
            db.setCommentText(p.getText());
            db.setCommentTimestamp(ConverterUtils.asSqlDate(p.getPosted()));
            if (p.getNewsId() != null) {
                DbNews dbnews = newsService.findById(p.getNewsId());
                if (dbnews != null) {
                    db.setNews(dbnews);
                }
            }
            if (p.getPostedBy() != null && p.getPostedBy().getId() != null) {
                long personId = p.getPostedBy().getId();
                DbPerson person = personService.findById(personId);
                if (person != null) {
                    db.setPerson(person);
                }
            }
            if (p.getPosted() != null) {
                db.setCommentTimestamp(ConverterUtils.asSqlDate(p.getPosted()));
            }

            return db;
        }
    }
        
    @Bean
    public NewsCommentFromDbToJson newsCommentFromDbToJson() {
        return new NewsCommentFromDbToJson();
    }
    
    @Bean
    public NewsCommentJsonToDbConverter newsCommentFromJsonToDb() {
        return new NewsCommentJsonToDbConverter();
    }
    
    private NewsCommentConverters() {
    }}
