package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.PersonService;
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
@RequestMapping(value = "/api/v1/profiles", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {
    private static final Logger logger = LoggerFactory.getLogger(PersonsApi.class);
	
	@Autowired
	private PersonService personService;

    @Autowired
    private PersonConverters.JsonToDbConverter personJsonToDb;

    @Autowired
    private PersonConverters.FromDbToJson personDbToJsonConverter;
    
    @Autowired
    private SearchParser searchParser;

    @Autowired
    private RequestHelper requestHelper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> get(
            @PathVariable("id") int id) throws NotFoundException {
        logger.info(" GET /api/v1/profiles/{}", id);
        DbPerson o = personService.findById(id);
        if (o == null) {
            logger.info(" GET /api/v1/profiles/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(personDbToJsonConverter.apply(o), HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
        logger.info("DELETE /api/v1/profiles/{}", id);
		if (!requestHelper.checkAdminRequestIfNeeded(id, request)) {
	        logger.info("DELETE /api/v1/profiles/{} FORBIDDEN", id);
			return new ResponseEntity<>("Only admin's can change others... ", HttpStatus.FORBIDDEN);
		}

		personService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> list(
	        @RequestParam(value = "aq", required = false) String aq,
            @RequestParam(value = "q", required = false) String q,
	        @RequestParam(value = "size", required = false) Integer size
	        ) throws NotFoundException {
        logger.info("GET /api/v1/profiles: q={}, aq={}, size={}", q, aq, size);
	    Iterable<DbPerson> all;
	    if (!Strings.isNullOrEmpty(q)) {
	        all = personService.findAll(q);
	    } else if (!Strings.isNullOrEmpty(aq)) {
	        List<SearchCriteria> criterias = searchParser.parse(aq);
            all = personService.findAll(criterias);
	    } else {
	        all = personService.findAll();
	    }
		
		FluentIterable<Person> iterable = FluentIterable.from(all)
				.transform(personDbToJsonConverter);
		// Support size parameter, but only if it's set (and not 0)
		if (size != null && size.intValue() > 0) {
		    iterable.limit(size);
		}
		return new ResponseEntity<>(iterable.toList(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") Long id,
			@RequestBody Person person,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
        logger.info("PUT /api/v1/profiles/{}", id);
        
		if (!requestHelper.checkAdminRequestIfNeeded(id, request)) {
	        logger.info("PUT /api/v1/profiles/{} FORBIDDEN", id);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (id.longValue() != person.getId().longValue()) {
	        logger.info("PUT /api/v1/profiles/{} BAD REQUEST {} != {}", id, id, person.getId());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbPerson db = personService.findById(id);
		if (db == null) {
            logger.info("PUT /api/v1/profiles/{} NOT FOUND", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		DbPerson updatedDbPerson = personJsonToDb.apply(db, person);
		personService.save(updatedDbPerson);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
