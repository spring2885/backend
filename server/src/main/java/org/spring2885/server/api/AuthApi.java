package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbToken;
import org.spring2885.server.db.service.TokenService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.mail.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = { APPLICATION_JSON_VALUE })
public class AuthApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthApi.class);

	@Autowired
	TokenService tokenService;
	@Autowired
	PersonService personService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Mailer mailer;
	@Value("${app.name}")
	private String appName;
	@Value("${app.forgot.from.name}")
	private String fromName;
	@Value("${app.forgot.reset.url}")
	private String resetUrl;

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@RequestParam("email") String email) throws NotFoundException {
	    logger.info("/auth/forgot: {}", email);

		DbPerson p = personService.findByEmail(email);

		if (p == null || !email.equals(p.getEmail())) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		// Delete the old tokens.
		tokenService.deleteByEmail(email);

		// Add a new token.
		UUID uuid = UUID.randomUUID();
		DbToken tokenData = new DbToken();
		tokenData.setEmail(email);
		tokenData.setUuid(uuid.toString());
		tokenData.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
		tokenService.save(tokenData);

		logger.info("Sending email to {} with token: {}", email, uuid);
		Map<String, String> data = new HashMap<>();
		data.put("name", p.getName());
		data.put("app_name", appName);
		data.put("reset_url", resetUrl);
		data.put("from_name", fromName);

		try {
			mailer.send(email, "forgot", "Forgot Password.", data);
		} catch (IOException | URISyntaxException e) {
			// If we can't send the email there is no reason to give the UUID
			// out.
			logger.error("Error sending email to" + email, e);
			throw new RuntimeException(e);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity<Void> reset(
	        @RequestParam("email") String email,
	        @RequestParam("token") String tokenString,
			@RequestParam("newPassword") String newPassword) throws Exception {
        logger.info("/auth/reset: email: {}, token: {}", email, tokenString);

		if (!tokenService.existsByEmail(email)) {
	        logger.info("/auth/reset: NOT FOUND email: {}, token: {}", email, tokenString);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

        DbPerson person = personService.findByEmail(email);
        if (person == null) {
            logger.info("/auth/reset: PERSON NOT FOUND email: {}, token: {}", email, tokenString);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

		List<DbToken> tokens = tokenService.findByEmail(email);
		DbToken savedToken = findToken(tokens, tokenString);
		if (savedToken == null) {
            logger.info("/auth/reset: TOKEN NOT FOUND email: {}, token: {}", email, tokenString);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		// At this point we looked up the saved token from the database and
		// found one.
		if (!savedToken.getEmail().equals(email)) {
			// Our saved token was for someone else.
            logger.info("/auth/reset: TOKEN MISMATCS email: {}, token: {}", email, tokenString);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// All good from here on out. Update the password and save the person.
		// Ideally these two would be transactional.
		String hashedPassword = passwordEncoder.encode(newPassword);
		person.setPassword(hashedPassword);
		personService.save(person);

		tokenService.delete(savedToken.getUuid());

		logger.info("Updated password for " + person.getEmail());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private DbToken findToken(Iterable<DbToken> tokens, String uuid) {
		for (DbToken t : tokens) {
			if (uuid.equals(t.getUuid())) {
				return t;
			}
		}
		return null;
	}
}
