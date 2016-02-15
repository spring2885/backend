package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/persons", produces = { APPLICATION_JSON_VALUE })
public class PersonsApi {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> personsGet(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		List<Person> persons = new ArrayList<>();
		Person p = new Person();
		p.setFirstname("Charles");
		p.setLastname("Babbage");
		persons.add(p);
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Void> personsPut(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "",	method = RequestMethod.POST)
	public ResponseEntity<Void> personsPost(@RequestBody Person person) throws NotFoundException {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
