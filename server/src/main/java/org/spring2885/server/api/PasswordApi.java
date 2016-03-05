package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbToken;
import org.spring2885.server.db.service.TokenService;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping(value = "/auth", produces = { APPLICATION_JSON_VALUE })
public class PasswordApi {
	
	@Autowired TokenService tokenService;
	@Autowired PersonService personService;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/forgot",method = RequestMethod.POST)
	public UUID personsResetToken(
			@RequestParam("email") String email
			) throws NotFoundException {
		
		//List<DbToken> tokens = tokenService.findByEmail(email);
		List<DbPerson> persons = personService.findByEmail(email);
		UUID uuidToken = null;
		DbToken tokenData = null;
		for (DbPerson p : persons){
			if (p.getEmail() != null && p.getEmail().equals(email)){
				uuidToken = UUID.randomUUID();
				tokenData = new DbToken();
				tokenData.setEmail(email);
				tokenData.setUuid(uuidToken.toString());
				tokenData.setUuidStatus("NEW");
				tokenService.save(tokenData);
			} else {
				throw new RuntimeException("email not found: " + email);
			}
		} 
		return uuidToken;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void resetPassword(
			@RequestParam("email") String email,
			@RequestParam("token") String token,
			@RequestParam("newPassword") String nPassword) throws Exception {
		UUID uuidToken = null;
		if (tokenService.existsByEmail(email)){
			List<DbPerson> persons = personService.findByEmail(email);
			List<DbToken> tokens = tokenService.findByEmail(email);
			
			if (!containsToken(tokens, token)) {
				throw new RuntimeException("token not found: " + token);
			} 
			
			/*for(DbToken t : tokenD){
				for(DbPerson p : person){
					if(t.getEmail().equals(p.getEmail())){
						if(token.equals(t.getUuid())){
							String hashedpassword = passwordEncoder.encode(nPassword);
							p.setPassword(hashedpassword);
							personService.save(p);
							t.setUuidStatus("used");
							tokenService.save(t);
						}
					}
				}
			}*/
						
			/*if (uuidToken != null && token.equals(uuidToken)){
				//String hashedPassword = passwordEncoder.encode(nPassword);
				//person.setPassword(hashedPassword);
				//token2.setUuidStatus("used");
			}*/			
		}
		
	}
	
	private boolean containsToken(Iterable<DbToken> tokens, String uuid) {
		  for (DbToken t : tokens) {
		    if (uuid.equals(t.getUuid())) { 
		      return true;
		    }
		  }
		  return false;
		}

}
