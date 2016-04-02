package org.spring2885.server.api;
//PUT, POST, GET, DELETE, and GET{id}

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.spring2885.model.Language;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.LanguageConverters;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/Language", produces = { APPLICATION_JSON_VALUE })
public class LanguageApi {

    @Autowired
    private LanguageService LanguageService;
    
    @Autowired
    private LanguageConverters.JsonToDbConverter languageJsonToDb;
    
    @Autowired
    private LanguageConverters.LanguageFromDbToJson dbToJsonConverter;
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<Language> get(
			@PathVariable("code") String code) throws NotFoundException {
		DbLanguage o = LanguageService.findByCode(code);
		if (o == null) {
			// When adding test testLanguagesByCode_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dbToJsonConverter.apply(o), HttpStatus.OK);

	}
//	@RequestMapping(method = RequestMethod.PUT)
//	@RequestMapping(method = RequestMethod.POST)
//	@RequestMapping(method = RequestMethod.DELETE)
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
}
