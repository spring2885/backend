package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.PersonType;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.PersonTypeConverter;
import org.spring2885.server.db.service.person.PersonTypeService;
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
@RequestMapping(value = "/api/v1/persontype", produces = { APPLICATION_JSON_VALUE })
public class PersonTypeApi {

    private static final Logger logger = LoggerFactory.getLogger(PersonTypeApi.class);

    @Autowired
	private PersonTypeService personTypeService;

    @Autowired
    private PersonTypeConverter.FromDbToJson personTypeFromDbToJson;
    
    @Autowired
    private PersonTypeConverter.JsonToDbConverter personTypeJsonToDb;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<DbPersonType>> list(
            ) throws NotFoundException {
        logger.info("GET /api/v1/persontype");
    	Iterable<DbPersonType> o = personTypeService.findAll();
        
        if (o == null) {
            // TODO(rob): This seems broken
            // When adding test testPersonsById_notFound, was getting a NullPointerException
            // here, so needed to add this.
            logger.info("GET /api/v1/persontype. WTF");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonType> get(@PathVariable("id") int id) throws NotFoundException {
        logger.info("GET /api/v1/persontype/{}", id);
        DbPersonType o = personTypeService.findById(id);
        if (o == null) {
            logger.info("GET /api/v1/persontype/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personTypeFromDbToJson.apply(o), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
        logger.info("DELETE /api/v1/persontype/{}", id);

    	if (!request.isUserInRole("ROLE_ADMIN")) {
            logger.info("DELETE /api/v1/persontype/{} FORBIDDEN", id);
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
		personTypeService.delete(id);
		if (personTypeService.findById(id) == null){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> put(
    		@PathVariable("id") Integer id,
    		SecurityContextHolderAwareRequestWrapper request,
    		@RequestBody PersonType personType) throws NotFoundException{
    	
        logger.info("PUT /api/v1/persontype/{} : {}", id, personType);
    	if (!request.isUserInRole("ROLE_ADMIN")) {
            logger.info("PUT /api/v1/persontype/{} FORBIDDEN", id);
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    	if (id.intValue() != personType.getId().intValue()) {
            logger.info("PUT /api/v1/persontype/{} BAD REQUEST", id);
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	DbPersonType db = personTypeService.findById(id);
    	if (db == null){
            logger.info("PUT /api/v1/persontype/{} NOT FOUND", id);
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	DbPersonType updatedDbPersonType = personTypeJsonToDb.apply(db, personType);
    	personTypeService.save(updatedDbPersonType);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> post(
    		@RequestBody PersonType type) throws NotFoundException {
        logger.info("POST /api/v1/persontype");
    	
    	if (personTypeService.existsByName(type.getName())) {
            logger.info("POST /api/v1/persontype");
    		throw new RuntimeException("name already exists: " + type.getName());
    	}
    	
    	DbPersonType updatedDbPersonType = personTypeJsonToDb.apply(new DbPersonType(), type);
    	personTypeService.save(updatedDbPersonType);
    	
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
