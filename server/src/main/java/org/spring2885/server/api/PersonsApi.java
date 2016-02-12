package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.spring2885.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/persons", produces = {APPLICATION_JSON_VALUE})
@Api(value = "/persons", description = "the persons API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-02-10T23:10:13.712-08:00")
public class PersonsApi {
  

  @ApiOperation(value = "", notes = "Gets `person` objects.\nOptional query param of **size** determines\nsize of returned array", response = Person.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Successful response") })
  	@RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Person>> personsGet(
		  @ApiParam(value = "Size of array", required = true) 
		  @RequestParam(value = "size", required = true) Double size)
      throws NotFoundException {
      // do some magic!
      // return new ResponseEntity<List<InlineResponse2001>>(HttpStatus.OK);
	  List<Person> persons = new ArrayList<>();
	  Person p = new Person();
	  p.setFirstname("Charles");
	  p.setLastname("Babbage");
	  persons.add(p);
      return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
  }

  

  @ApiOperation(value = "", notes = "", response = Void.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Updates the person") })
  @RequestMapping(value = "", 
    
    
    method = RequestMethod.PUT)
  public ResponseEntity<Void> personsPut(

@ApiParam(value = "The person you want to update" ,required=true ) @RequestBody Person person
)
      throws NotFoundException {
      // do some magic!
      return new ResponseEntity<Void>(HttpStatus.OK);
  }

  

  @ApiOperation(value = "", notes = "", response = Void.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Make a new person") })
  @RequestMapping(value = "", 
    
    
    method = RequestMethod.POST)
  public ResponseEntity<Void> personsPost(

@ApiParam(value = "The person you want to post" ,required=true ) @RequestBody Person person
)
      throws NotFoundException {
      // do some magic!
      return new ResponseEntity<Void>(HttpStatus.OK);
  }

  
}
