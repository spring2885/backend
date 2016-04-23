package org.spring2885.server.api;
//PUT, POST, GET, DELETE, and GET{code}

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.spring2885.model.Language;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.LanguageConverters;
import org.spring2885.server.db.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/language", produces = { APPLICATION_JSON_VALUE })
public class LanguageApi {

	@Autowired
	private LanguageService languageService;

	@Autowired
	private LanguageConverters.JsonToDbConverter languageJsonToDb;

	@Autowired
	private LanguageConverters.LanguageFromDbToJson languageDbToJson;

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<Language> get(@PathVariable("code") String code) throws NotFoundException {
		DbLanguage o = languageService.findByCode(code);
		if (o == null) {
			// When adding test testLanguagesByCode_notFound, was getting a
			// NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(languageDbToJson.apply(o), HttpStatus.OK);

	}

	@RequestMapping(value = "/{code}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(@PathVariable("code") String code, @RequestBody Language lang,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		DbLanguage db = languageService.findByCode(code);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		DbLanguage updatedDbLanguageService = languageJsonToDb.apply(db, lang);
		languageService.save(updatedDbLanguageService);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Post(@RequestBody Language lang) throws NotFoundException {
		
        DbLanguage updatedDbLanguage = languageJsonToDb.apply(new DbLanguage(), lang);
        languageService.save(updatedDbLanguage);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}