package org.spring2885.server.api;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.News;
import org.spring2885.model.SocialService;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbSocialService;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.model.SocialServiceConverters;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.SocialServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.FluentIterable;

@RestController
@RequestMapping(value = "/api/v1/socialservice", produces = { APPLICATION_JSON_VALUE })
public class SocialServicesApi {
	private static final Logger logger = LoggerFactory.getLogger(NewsApi.class);
	
	@Autowired
	private SocialServiceService socialServiceService;
	
	@Autowired
	private SocialServiceConverters.JsonToDbConverter socialServiceJsonToDb;
	
	@Autowired
	private SocialServiceConverters.SocialServiceFromDbToJson dbToJsonConverter;
	
	

}
