package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
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

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {
    private static final Logger logger = LoggerFactory.getLogger(PersonsApi.class);
	
	@Autowired
	private PersonService personService;

    @Autowired
    private PersonConverters.JsonToDbConverter jsonToDbConverter;

    @Autowired
    private PersonConverters.FromDbToJson dbToJsonConverter;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> get(
            @PathVariable("id") int id) throws NotFoundException {
        DbPerson o = personService.findById(id);
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
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			String error = 
					"Only admin's can change others... Read this: "
					+ "God, grant me the serenity to accept the things I cannot change,"
					+ "Courage to change the things I can,"
					+ "And wisdom to know the difference.";
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
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
	    logger.info("PersonsApi GET: q={}, aq={}, size={}", q, aq, size);
	    Iterable<DbPerson> all;
	    if (q != null && q.length() > 0) {
	        all = personService.findAll(q);
	    } else if (aq != null && aq.length() > 0) {
	        SearchParser parser = new SearchParser();
	        List<SearchCriteria> criterias = parser.parse(aq);
            all = personService.findAll(criterias);
	    } else {
	        all = personService.findAll();
	    }
		
		FluentIterable<Person> iterable = FluentIterable.from(all)
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
			@RequestBody Person person,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (id.intValue() != person.getId().intValue()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbPerson db = personService.findById(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		jsonToDbConverter.withDbPerson(db);
		DbPerson updatedDbPerson = jsonToDbConverter.apply(person);
		personService.save(updatedDbPerson);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private boolean checkAdminRequestIfNeeded(int requestId, SecurityContextHolderAwareRequestWrapper request) {
		if (!request.isUserInRole("ROLE_ADMIN")) {
			// Only admin's can change other profiles.
			String name = request.getUserPrincipal().getName();
			List<DbPerson> possibles = personService.findByEmail(name);
			if (possibles.size() != 1) {
				// I can't find myself... need more zen.
				return false;
			}
			
			DbPerson me = Iterables.getOnlyElement(possibles);
			if (me.getId() != requestId) {
				// Someone is being naughty...
				return false;
			}
		}
		return true;
	}

}
