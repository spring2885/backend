package org.spring2885.server.api;
//PUT, POST, GET, DELETE, and GET{code}
/* This LanguageApi should be working, however, it is not hooked up with the front end.
 * The LanguageApiTest that corresponds with this class shows that it works.
 */

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Language;
import org.spring2885.model.Person;
import org.spring2885.model.SocialService;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.LanguageConverters;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchParser;
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

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;

@RestController
@RequestMapping(value = "/api/v1/language", produces = { APPLICATION_JSON_VALUE })
public class LanguageApi {
	//Added Logger
	private static final Logger logger = LoggerFactory.getLogger(LanguageApi.class);
	
	@Autowired
	private LanguageService languageService;

	@Autowired
	private LanguageConverters.JsonToDbConverter languageJsonToDb;

	@Autowired
	private LanguageConverters.LanguageFromDbToJson languageDbToJson;
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<Language> get(
			@PathVariable("code") String code) throws NotFoundException {
		logger.info("GET /api/v1/language/{}", code);
		DbLanguage o = languageService.findByCode(code);
		if (o == null) {
			// When adding test testLanguagesByCode_notFound, was getting a
			// NullPointerException
			// here, so needed to add this.
			logger.info("GET /api/v1/language/{} NOT FOUND", code);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(languageDbToJson.apply(o), HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Language>> list(
			@RequestParam(value = "size", required = false) Integer size)
			throws NotFoundException {
		
		Iterable<DbLanguage> all;
		all = languageService.findAll();
		
		for(DbLanguage l : all)
		{
			logger.trace("language={}", l.toString());
		}
		
		FluentIterable<Language> iterable = FluentIterable.from(all)
				.transform(languageDbToJson);
		
		if(size!= null && size.intValue() > 0) {
			iterable.limit(size);
		}
		
		return new ResponseEntity<>(iterable.toList(), HttpStatus.OK);
		
		/*List<Language> l = FluentIterable.from(languageService.findAll())
				.transform(languageDbToJson)
				.toList();
		return new ResponseEntity<>(l, HttpStatus.OK);*/
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(@PathVariable("code") String code, @RequestBody Language lang,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		logger.info("PUT /api/v1/language/{}", code);
		
		
		if(!code.equalsIgnoreCase(lang.getCode())){
			logger.info("PUT /api/v1/language/{} BAD REQUEST", code);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		DbLanguage db = languageService.findByCode(code);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//Language Table not connected to Persons Table so not sure how to check Admin request 
		//Changed HttpStatus from "OK" to "FORBIDDEN"
		if(code.equalsIgnoreCase(lang.getCode())){
			logger.info("PUT /api/v1/language/{} FORBIDDEN", code);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		DbLanguage updatedDbLanguageService = languageJsonToDb.apply(db, lang);
		
		languageService.save(updatedDbLanguageService);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> post(
    		@RequestBody Language lang) throws NotFoundException {
		
		logger.info("POST /api/v1/language '{}'", lang);
        DbLanguage updatedDbLanguage = languageJsonToDb.apply(new DbLanguage(), lang);
        languageService.save(updatedDbLanguage);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
