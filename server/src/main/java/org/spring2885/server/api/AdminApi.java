package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.AbuseRequest;
import org.spring2885.model.ApprovalRequest;
import org.spring2885.model.FacultyRequest;
import org.spring2885.model.Verdict;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/approvals", produces = { APPLICATION_JSON_VALUE })
public class AdminApi {
    private static final Logger logger = LoggerFactory.getLogger(JobsApi.class);
    
    @RequestMapping(value = "/request/faculty", method = RequestMethod.POST)
    public ResponseEntity<Void> teacher(
            @RequestBody FacultyRequest request,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        
        logger.info(request.toString());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }    

    @RequestMapping(value = "/request/abuse", method = RequestMethod.POST)
    public ResponseEntity<Void> abuse(
            @RequestBody AbuseRequest request,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        
        logger.info(request.toString());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }    

    @RequestMapping(value = "/verdict/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> abuse(
            @PathVariable("id") Long id,
            @RequestBody Verdict request,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        
        logger.info(request.toString());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }   
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ApprovalRequest>> list(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "type", required = false) String type
            ) throws NotFoundException {
        logger.info("AdminApi list : state={}, type={}", state, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
