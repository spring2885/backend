package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.FluentIterable;

@Controller
@RequestMapping(value = "/api/persons", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {
	
	// TODO(rcleveng): Move this into a helper class.
	static Person toJsonPerson(DbPerson o) {
		Person p = new Person();
		p.setId((long)o.getId());
		p.setFirstname(o.getName());
		p.setEmail(o.getEmail());
		return p;
	}
	
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> get(@PathVariable("id") int id)
			throws NotFoundException {
		DbPerson o = personService.findById(id);
		return new ResponseEntity<Person>(toJsonPerson(o), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		
		List<Person> persons = FluentIterable.from(personService.findAll())
				.transform((DbPerson o) -> { return toJsonPerson(o); })
				.toList();
		
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> put(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> post(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
