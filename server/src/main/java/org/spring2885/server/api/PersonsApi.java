package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
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

@Controller
@RequestMapping(value = "/api/persons", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {
	
	static Person toJsonPerson(org.spring2885.server.db.model.Person o) {
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
		org.spring2885.server.db.model.Person o = personService.findById(1);
		Person p = toJsonPerson(o);
		return new ResponseEntity<Person>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		List<Person> persons = new ArrayList<>();
		for (org.spring2885.server.db.model.Person o : personService.findAll()) {
			persons.add(toJsonPerson(o));
		}
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "",	method = RequestMethod.POST)
	public ResponseEntity<Void> personsPost(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
