package org.spring2885.server.api;

import static com.google.common.base.Preconditions.checkState;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.News;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;

@RestController
@RequestMapping(value = "/api/v1/news", produces = { APPLICATION_JSON_VALUE })
public class NewsApi {
	private static final Logger logger = LoggerFactory.getLogger(NewsApi.class);
	
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private NewsConverters.JsonToDbConverter newsJsonToDb;
    
    @Autowired
    private NewsConverters.NewsFromDbToJson dbToJsonConverter;
    
    @Autowired
    private SearchParser searchParser;
    
    @Autowired
    private RequestHelper requestHelper;
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<News> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbNews o = newsService.findById(id);
		if (o == null) {
			// When adding test testPersonsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dbToJsonConverter.apply(o), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
        DbNews db = newsService.findById(id);
        if (db == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

		newsService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<News>> list(
			@RequestParam(value = "aq", required = false) String aq,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "admin", required = false, defaultValue="false") boolean adminRequest,
	        @RequestParam(value = "size", required = false) Integer size,
	        SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		logger.info("NewsApi GET: q={}, aq={}, size={}", q, aq, size);
		
		if (adminRequest && !requestHelper.isAdminRequest(request)) {
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		DbPerson me = requestHelper.loggedInUser(request);
		
	    Iterable<DbNews> all;
	    if (!Strings.isNullOrEmpty(q)) {
	        all = newsService.findAll(me, adminRequest, q);
	    } else if (!Strings.isNullOrEmpty(aq)) {
	        List<SearchCriteria> criterias = searchParser.parse(aq);
            all = newsService.findAll(me, adminRequest, criterias);
	    } else {
	        all = newsService.findAll(me, adminRequest);
	    }
		
	    for (DbNews n : all) {
	        logger.info("news={}", n.toString());
	    }
	    
		FluentIterable<News> iterable = FluentIterable.from(all)
				.transform(dbToJsonConverter);
		// Support size parameter, but only if it's set (and not 0)
		if (size != null && size.intValue() > 0) {
		    iterable.limit(size);
		}
		return new ResponseEntity<>(iterable.toList(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") Integer id,
			@RequestBody News news,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (id.longValue() != news.getId().longValue()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

        DbNews db = newsService.findById(id);
        if (db == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
		
		DbNews updatedDbNews = newsJsonToDb.apply(db, news);
		
		newsService.save(updatedDbNews);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> post(
			@RequestBody News news,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {

	    // Look up the currently logged in user
        DbPerson me = requestHelper.loggedInUser(request);
        checkState(me != null);

	    // Create our DbNews version
	    DbNews db = newsJsonToDb.apply(new DbNews(), news);
        // Since we are doing a post, set defaults.
	    db.setId(null);
	    db.setPersonId(me);
	    Timestamp now = Timestamp.from(Instant.now());
	    db.setPosted(now);
	    
	    if (db.getVisibleToPersonTypes().isEmpty()) {
	        // If it's not visible to anyone, make it visible to
	        // the same group we are in.
	        db.setVisibleToPersonType(me.getType());
	    }
	    
		newsService.save(db);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
