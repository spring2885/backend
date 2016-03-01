package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.SecureRandom;
import java.util.UUID;

import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbToken;
import org.spring2885.server.db.service.TokenService;
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

	/*
	 * CHANGE TO TOKENSERVICE
	 */
	
	@Autowired TokenService tokenService;
	@Autowired private PasswordEncoder passwordEncoder;
	UUID uuidToken = null;
	
	@RequestMapping(value = "/forgot",method = RequestMethod.POST)
	public /*ResponseEntity<Void>*/ UUID personsResetToken(
			@RequestParam("email") String email
			) throws NotFoundException {
		//String token = "";
		//UUID uuidToken = null;
		DbToken token = new DbToken();
		if (tokenService.existsByEmail(email)) {
			/*
			 * uuidToken is assigned a random uuid.
			 * the if statement checks to see if it exists in the database? (how to do this)
			 * if it exists, it creates a new one again (though this should never have to occur)
			 * how to save UUID in database.
			 */
			uuidToken = UUID.randomUUID();
			if(uuidToken.equals(token.getUuid())){
				uuidToken = UUID.randomUUID();
				token.setUuidStatus("new");
			}
			
			//return token;
		} else {
			throw new RuntimeException("email not found: " + email);
		}
		//return token;
		return uuidToken;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void resetPassword(
			@RequestParam("email") String email,
			@RequestParam("token") String token,
			@RequestParam("newPassword") String nPassword) throws Exception {
		
		if(tokenService.existsByEmail(email)){
			//need to verify that the token is correct
			DbPerson person = new DbPerson();
			DbToken token2 = new DbToken();
			
			//need to figure out how to access database to get the UUID
			
			if(uuidToken != null && token.equals(uuidToken)){
				String hashedPassword = passwordEncoder.encode(nPassword);
				person.setPassword(hashedPassword);
				token2.setUuidStatus("used");
			}			
		}
		
	}
	
	//used for random token
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
}
