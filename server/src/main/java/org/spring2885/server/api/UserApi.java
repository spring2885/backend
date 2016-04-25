package org.spring2885.server.api;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.User;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonConverters.FromDbToJson personDbToJsonConverter;

    @RequestMapping("/user")
    public ResponseEntity<User> user(Principal principal) {
        String email = principal.getName();
        logger.info("GET /user: {}", email);

        DbPerson me = personService.findByEmail(email);
        if (me == null) {
            logger.info("GET /user: {} NOT FOUND", email);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        User user = new User(principal, personDbToJsonConverter.apply(me));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
