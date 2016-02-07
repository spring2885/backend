package org.spring2885.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class StaticController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
    	return "<html><head><title>Spring2885 API Server</title></head>"
    	+ "<body>"
        + "<div style=\"text-align: center ;\">"
        + "<img src=\"/img/face.png\">"
    	+ "<h1>Spring2885 API Server</h1>"
    	+ "<a href=\"/api-docs/index.html\">API Documentation</a><br/>"
    	+ "</div>"
    	+ "</body></html>";
    }
}
