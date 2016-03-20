package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.PersonType;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.PersonTypeConverter;
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
    private PersonTypeConverter.FromDbToJson personTypeFromDbToJson;
    
    @Autowired
    private PersonTypeConverter.JsonToDbConverter personTypeJsonToDb;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<DbPersonType>> list(
            ) throws NotFoundException {
    	Iterable<DbPersonType> o = personTypeService.findAll();
        
        if (o == null) {
            // When adding test testPersonsById_notFound, was getting a NullPointerException
            // here, so needed to add this.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonType> get(
            @PathVariable("id") int id) throws NotFoundException {
    	Iterable<DbPersonType> o = personTypeService.findAll();
    	if (o != null) {
    	  for (DbPersonType p : o) {
    	    // Note to Matt: You want to return the JSON model of a personType, NOT the DbPersonType
    	    if (id == p.getId()) { return new ResponseEntity<>(personTypeFromDbToJson.apply(p), HttpStatus.OK); }
    	  }
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {

    	//TODO: verify admin request
    	//if they are not an admin, return HttpStatus.FORBIDDEN;
		personTypeService.delete(id);
		if (personTypeService.findById(id) == null){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> put(
    		@PathVariable("id") Integer id,
    		@RequestBody PersonType personType) throws NotFoundException{
    	
    	//TODO: verify admin request
    	//if they are not an admin, return HttpStatus.FORBIDDEN;
    	if (id.intValue() != personType.getId().intValue()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	DbPersonType db = personTypeService.findById(id);
    	if (db == null){
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	personTypeJsonToDb.withDbPersonType(db);
    	DbPersonType updatedDbPersonType = personTypeJsonToDb.apply(personType);
    	personTypeService.save(updatedDbPersonType);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<Void> personTypePost(
    		@RequestParam("id") Integer id,
    		@RequestParam("name") String name
    		) throws NotFoundException {
    	
    	if (personTypeService.existsByName(name)) {
    		throw new RuntimeException("name already exists: " + name);
    	}
    	
    	DbPersonType personType = new DbPersonType();
    	personType.setId(id);
    	personType.setName(name);
    	personTypeService.save(personType);
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
