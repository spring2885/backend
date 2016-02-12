package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.spring2885.model.InlineResponse200;
import org.spring2885.model.Job;
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
@RequestMapping(value = "/jobs", produces = {APPLICATION_JSON_VALUE})
@Api(value = "/jobs", description = "the jobs API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-02-10T23:10:13.712-08:00")
public class JobsApi {
  

  @ApiOperation(value = "", notes = "Gets `Job` objects.\nOptional query param of **size** determines\nsize of returned array", response = InlineResponse200.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Successful response") })
  @RequestMapping(value = "", 
    
    
    method = RequestMethod.GET)
  public ResponseEntity<List<InlineResponse200>> jobsGet(@ApiParam(value = "Size of array", required = true) @RequestParam(value = "size", required = true) Double size


)
      throws NotFoundException {
      // do some magic!
      return new ResponseEntity<List<InlineResponse200>>(HttpStatus.OK);
  }

  

  @ApiOperation(value = "", notes = "", response = Void.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Updates the Job") })
  @RequestMapping(value = "", 
    
    
    method = RequestMethod.PUT)
  public ResponseEntity<Void> jobsPut(

@ApiParam(value = "The Job you want to update" ,required=true ) @RequestBody Job job
)
      throws NotFoundException {
      // do some magic!
      return new ResponseEntity<Void>(HttpStatus.OK);
  }

  

  @ApiOperation(value = "", notes = "", response = Void.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Make a new Job") })
  @RequestMapping(value = "", 
    
    
    method = RequestMethod.POST)
  public ResponseEntity<Void> jobsPost(

@ApiParam(value = "The Job you want to post" ,required=true ) @RequestBody Job job
)
      throws NotFoundException {
      // do some magic!
      return new ResponseEntity<Void>(HttpStatus.OK);
  }

  
}
