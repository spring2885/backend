package org.spring2885.server.api;

import static com.google.common.base.Preconditions.checkState;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.NewsComment;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbNewsComment;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.NewsCommentConverters;
import org.spring2885.server.db.service.NewsCommentService;
import org.spring2885.server.db.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/news_comment", produces = { APPLICATION_JSON_VALUE })
public class NewsCommentApi {
    private static final Logger logger = LoggerFactory.getLogger(NewsApi.class);

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private NewsCommentService newsCommentService;
    
    @Autowired
    private NewsCommentConverters.NewsCommentFromDbToJson newsCommentFromDbToJson;
    
    @Autowired
    private NewsCommentConverters.NewsCommentJsonToDbConverter newsCommentJsonToDb;
    
    @Autowired
    private RequestHelper requestHelper;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> post(
            @RequestBody NewsComment news_comment,
            SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
        logger.info("POST /api/v1/news_comment: {}", news_comment);

        // Look up the currently logged in user
        DbPerson me = requestHelper.loggedInUser(request);
        checkState(me != null);

        Long newsId = news_comment.getNewsId();
        DbNews news = newsService.findById(newsId);
        if (news == null) {
            logger.info("POST /api/v1/news_comment: News ID: {} NOT FOUND", newsId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Create our DbNews version
        DbNewsComment db = newsCommentJsonToDb.apply(new DbNewsComment(), news_comment);
        // Since we are doing a post, set defaults.
        db.setId(null);
        db.setPerson(me);
        db.setCommentTimestamp(new Date(System.currentTimeMillis()));
        db.setNews(news);

        newsCommentService.save(db);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NewsComment> get(
            @PathVariable("id") int id) throws NotFoundException {
        logger.info("GET /api/v1/news_comment/{}", id);
        DbNewsComment o = newsCommentService.findById(id);
        if (o == null) {
            logger.info("GET /api/v1/news_comment/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsCommentFromDbToJson.apply(o), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(
            @PathVariable("id") Integer id,
            SecurityContextHolderAwareRequestWrapper request)
            throws NotFoundException {
        logger.info("DELETE /api/v1/news_comment/{}", id);
        
        DbNewsComment db = newsCommentService.findById(id);
        if (db == null) {
            logger.info("DELETE /api/v1/news_comment/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            logger.info("GET /api/v1/news_comment/{} FORBIDDEN", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        newsCommentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> put(
            @PathVariable("id") Integer id,
            @RequestBody NewsComment news,
            SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
        
        logger.info("PUT /api/v1/news_comment/{}: {}", id, news);
        if (id.longValue() != news.getId().longValue()) {
            logger.info("news comment ID {} didn't match JSON content ID {}", id, news.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DbNewsComment db = newsCommentService.findById(id);
        if (db == null) {
            logger.info("PUT /api/v1/news_comment/{}: NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            logger.info("person id: {} unable to access article authored by {}", 
                    request.getUserPrincipal().getName(), db.getPerson().getId());
            logger.info("PUT /api/v1/news_comment/{}: FORBIDDEN", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        DbNewsComment updated = newsCommentJsonToDb.apply(db, news);
        newsCommentService.save(updated);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
