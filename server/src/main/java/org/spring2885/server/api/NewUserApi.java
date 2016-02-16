package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.spring2885.server.api.exceptions.NotFoundException;
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

	@Autowired
	private DataSource ds;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(method=RequestMethod.GET)
	public String newuser() {
		return "newuser";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> personsPost(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			) throws NotFoundException {
		
		try (Connection c = ds.getConnection()) {
			PreparedStatement ps = c.prepareStatement("INSERT INTO person(email, password) VALUES(?, ?)");
			ps.setString(1,  email);
			String hashedPassword = passwordEncoder.encode(password);
			ps.setString(2, hashedPassword);
			int rowsAdded = ps.executeUpdate();
			if (rowsAdded != 1) {
				System.err.println("User wasn't added.");
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			System.err.printf("User %s added with hashed pw; '%s'\n", email, hashedPassword);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
