package org.spring2885.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    //@RequestMapping(value="/login", method=RequestMethod.GET) 
    ModelAndView index() {
    	Map<String, String> data = new HashMap<>();
    	data.put("name", "Spring2885 API Server");
    	return new ModelAndView("login", data);
    }
}
