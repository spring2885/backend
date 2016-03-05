package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.DbSocialService;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.PersonService;
import org.spring2885.server.db.service.PersonTypeService;
import org.spring2885.server.db.service.SocialServiceService;
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
@RequestMapping(value = "/api/v1/profiles", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {
	
	@Autowired
	private PersonService personService;

	@Autowired
	private PersonConverters.JsonToDbConverter jsonToDbConverter;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbPerson o = personService.findById(id);
		if (o == null) {
			// When adding test testPersonsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(PersonConverters.fromDbToJson().apply(o), HttpStatus.OK);
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
	public ResponseEntity<List<Person>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		
		Function<DbPerson, Person> fromDbToJson = PersonConverters.fromDbToJson();
		List<Person> persons = FluentIterable.from(personService.findAll())
				.transform(fromDbToJson)
				.toList();
		
		return new ResponseEntity<>(persons, HttpStatus.OK);
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
