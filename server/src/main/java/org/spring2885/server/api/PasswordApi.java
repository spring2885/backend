package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.UUID;

import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbToken;
import org.spring2885.server.db.service.TokenService;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = { APPLICATION_JSON_VALUE })
public class PasswordApi {
	
	@Autowired TokenService tokenService;
	@Autowired PersonService personService;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/forgot",method = RequestMethod.POST)
    public UUID personsResetToken(@RequestParam("email") String email) throws NotFoundException {
		
        DbPerson p = personService.findByEmail(email);
        if (p == null || !email.equals(p.getEmail()) ) {
            throw new RuntimeException("email not found: " + email);
        }
        
	    UUID uuidToken = UUID.randomUUID();
		DbToken tokenData = new DbToken();
		tokenData.setEmail(email);
		tokenData.setUuid(uuidToken.toString());
		tokenData.setUuidStatus("NEW");
		tokenService.save(tokenData);
		return uuidToken;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void resetPassword(
			@RequestParam("email") String email,
			@RequestParam("token") String tokenString,
			@RequestParam("newPassword") String newPassword) throws Exception {
		if (tokenService.existsByEmail(email)){
			List<DbToken> tokens = tokenService.findByEmail(email);
			
			DbToken savedToken = findToken(tokens, tokenString);
			if (savedToken == null) {
				throw new RuntimeException("token not found: " + tokenString);
			} 
            DbPerson person = personService.findByEmail(email);
            if (person == null) {
                throw new RuntimeException("Person not found for email address: " + email);
            }
            
            // At this point we looked up the saved token from the database and found
            // one.  
            
            if (!savedToken.getEmail().equals(email)) {
                // Our saved token was for someone else.
                throw new RuntimeException("token does not match email address: " + tokenString);
            }
            
            // All good from here on out.  Update the password and save the person.
            // Ideally these two would be transactional.
            String hashedPassword = passwordEncoder.encode(newPassword);
            person.setPassword(hashedPassword);
            personService.save(person);
            
            savedToken.setUuidStatus("USED");
            tokenService.save(savedToken);
		}
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
