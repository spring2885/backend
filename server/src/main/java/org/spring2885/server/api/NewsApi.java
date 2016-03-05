package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.spring2885.model.News;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.person.PersonService;
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

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

@RestController
@RequestMapping(value = "/api/v1/news", produces = { APPLICATION_JSON_VALUE })
public class NewsApi {
	
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private PersonService personService;
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<News> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbNews o = newsService.findById(id);
		if (o == null) {
			// When adding test testPersonsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(NewsConverters.fromDbToJson().apply(o), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			String error = 
					"Only admin's can change others... Read this: "
					+ "God, grant me the serenity to accept the things I cannot change,"
					+ "Courage to change the things I can,"
					+ "And wisdom to know the difference.";
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		newsService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<News>> list(
	        @RequestParam(value = "size", required = false) Integer size)
			throws NotFoundException {
		
		Function<DbNews, News> fromDbToJson = NewsConverters.fromDbToJson();
		List<News> news = FluentIterable.from(newsService.findAll())
				.transform(fromDbToJson)
				.toList();
		
		return new ResponseEntity<>(news, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") Integer id,
			@RequestBody News news,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (id.intValue() != news.getId().intValue()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbNews db = newsService.findById(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		DbNews updatedDbNews = NewsConverters.fromJsonToDb()
				.withDbNews(db)
				.apply(news);
		newsService.save(updatedDbNews);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> put(@RequestBody News news) throws NotFoundException {
        
        DbNews db = new DbNews();
        DbNews updatedDbNews = NewsConverters.fromJsonToDb()
                .withDbNews(db)
                .apply(news);
        newsService.save(updatedDbNews);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
   	private boolean checkAdminRequestIfNeeded(int requestId, SecurityContextHolderAwareRequestWrapper request) {
		if (!request.isUserInRole("ROLE_ADMIN")) {
			// Only admin's can change other profiles.
			String email = request.getUserPrincipal().getName();
			DbPerson me = personService.findByEmail(email);
			if (me == null) {
				// I can't find myself... need more zen.
				return false;
			}
			
			if (me.getId() != requestId) {
				// Someone is being naughty...
				return false;
			}
		}
		return true;
	}

}
