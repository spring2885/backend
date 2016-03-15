package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
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
@RequestMapping(value = "/api/v1/persontype", produces = { APPLICATION_JSON_VALUE })
public class PersonTypeApi {
	private static final Logger logger = LoggerFactory.getLogger(PersonsApi.class);
	
	@Autowired
	private PersonTypeService personTypeService;

    @Autowired
    private PersonConverters.JsonToDbConverter personJsonToDb;

    @Autowired
    private PersonConverters.FromDbToJson dbToJsonConverter;
    
    @Autowired
    private SearchParser searchParser;

    /*@RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ResponseEntity<DbPersonType> get(
            @PathVariable("type") String type) throws NotFoundException {
    	//findById() returns a person type by id
    	Set<DbPersonType> o = personTypeService.findAll();
        
        //return o;
        if (o == null) {
            // When adding test testPersonsById_notFound, was getting a NullPointerException
            // here, so needed to add this.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
    
    /**
     * get a list of PersonTypes
     */
    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public ResponseEntity<DbPersonType> list(
            ) throws NotFoundException {
    	//findById() returns a person type by id
    	Set<DbPersonType> o = personTypeService.findAll();
        
        //return o;
        if (o == null) {
            // When adding test testPersonsById_notFound, was getting a NullPointerException
            // here, so needed to add this.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
