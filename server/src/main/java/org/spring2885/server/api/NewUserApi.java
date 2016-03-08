package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * API for {@code /newuser}.  
 * 
 * This API will create a new user in the system.
 */
@Controller
@RequestMapping(value = "/newuser", produces = { APPLICATION_JSON_VALUE })
public class NewUserApi {

	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired PersonService personService;
	@Autowired PersonTypeService personTypeService;
	
	/**
	 * Mapping for /newuser webpage. This is where a stub page
	 * for generating a new user lives.  It's implemented in
	 * {@code newuser.html}
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String newuser() {
		return "newuser";
	}

	/**
	 * Mapping for /newuser (POST) API Handler.
	 * <p>
	 * This creates a new user in the system.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> personsPost(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			) throws NotFoundException {
		
		if (personService.existsByEmail(email)) {
			throw new RuntimeException("email already exists: " + email);
		}
		
		DbPerson person = new DbPerson();
		person.setEmail(email);
		String hashedPassword = passwordEncoder.encode(password);
		person.setPassword(hashedPassword);
		person.setType(personTypeService.defaultType());
		personService.save(person);
		System.err.printf("User %s added with hashed pw; '%s'\n", email, hashedPassword);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
