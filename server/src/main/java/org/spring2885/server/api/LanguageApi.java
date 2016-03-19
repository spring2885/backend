package org.spring2885.server.api;
//PUT, POST, GET, DELETE, and GET{id}

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LanguageApi {

	@RequestMapping(method = RequestMethod.GET);
	@RequestMapping(method = RequestMethod.PUT)
	@RequestMapping(method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET);
}
