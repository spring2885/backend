package org.spring2885.server.web;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Value("${app.name}")
    private String appName;
    
    @RequestMapping( value = { "/home", "/" })
    public String home(Map<String, Object> model) {
		model.put("message",
			"This server is only for service APIs.\n"
            + "It is not intended to be accessed from a browser... \n"
            + "These are not the droids you are looking for... yada yada..");
		model.put("title", appName);
		model.put("date", new Date());
		return "home";
    }
}