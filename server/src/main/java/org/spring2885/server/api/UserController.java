package org.spring2885.server.api;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/user")
    public Principal user(Principal user) {
        logger.info("Handling /user with: {}", user.toString());
        return user;
    }
}
