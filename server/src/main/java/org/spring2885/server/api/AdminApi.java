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
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.ApprovalConverters;
import org.spring2885.server.db.model.DbApprovalRequest;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.ApprovalRequestService;
import org.spring2885.server.db.service.ApprovalTypes;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;

@RestController
@RequestMapping(value = "/api/v1/approvals", produces = { APPLICATION_JSON_VALUE })
public class AdminApi {
    private static final Logger logger = LoggerFactory.getLogger(JobsApi.class);
    
    @Autowired
    private ApprovalConverters.FacultyRequestToDbApproval facultyRequestToDbApproval;
    
    @Autowired
    private ApprovalConverters.ApprovalFromDbToJson approvalFromDbToJson;
    
    @Autowired
    private ApprovalConverters.VerdictToDbApproval verdictToDbApproval;
    
    @Autowired
    private ApprovalRequestService approvalRequestService;
    
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonTypeService personTypeService;

    @Autowired
    private RequestHelper requestHelper;

    @RequestMapping(value = "/request/faculty", method = RequestMethod.POST)
    public ResponseEntity<Void> faculty(
            @RequestBody FacultyRequest f,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        logger.info(f.toString());
        
        DbPerson loggedInUser = requestHelper.loggedInUser(wrapper);
        
        DbApprovalRequest db = facultyRequestToDbApproval.create(f, loggedInUser);
        approvalRequestService.save(db);
        logger.info("Saved request: {}", db.getUuid());
        
        return new ResponseEntity<Void>(HttpStatus.OK);
    }    

    @RequestMapping(value = "/request/abuse", method = RequestMethod.POST)
    public ResponseEntity<Void> abuse(
            @RequestBody AbuseRequest request,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        
        logger.info(request.toString());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }    

    @RequestMapping(value = "/verdict", method = RequestMethod.POST)
    public ResponseEntity<Void> verdict(
            @RequestBody Verdict verdict,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        
        logger.info(verdict.toString());
        if (!wrapper.isUserInRole("ROLE_ADMIN")) {
            logger.info("/verdict: User {} is not an admin, returning forbidden", wrapper.getRemoteUser());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        DbApprovalRequest request = approvalRequestService.findById(verdict.getId());
        String type = request.getApprovalType();
        if (!ApprovalTypes.isValidType(type)) {
            logger.warn("/verdict type '{}' not valid.", type);
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        
        if (!request.getActive().booleanValue()) {
            logger.warn("/verdict attempted to commit verdict on inactive request id: {}", 
                    verdict.getId());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        
        // In all cases, update the verdict.
        DbPerson approver = requestHelper.loggedInUser(wrapper);
        DbApprovalRequest updated = verdictToDbApproval.create(request, verdict, approver);
        
        if (ApprovalTypes.FACULTY.equals(type)) {
            if (verdict.getApproved().booleanValue()) {
                // Person approved to be faculty.
                DbPerson requestor = personService.findById(updated.getItemId());
                logger.info("Approving {} ({}) as a faculty member.", 
                        requestor.getName(), requestor.getEmail());
                requestor.setType(personTypeService.facultyType());
                personService.save(requestor);
            } else {
                // Person rejected. Nothing to do.
                // TODO: Maybe email them to let them know?
            }
        } else if (ApprovalTypes.ABUSE.equals(type)) {
            // TODO: Implement me.
            if (verdict.getApproved().booleanValue()) {
                // Set active=0 for the item.
            } else {
                // set abuse=0 for the item.
                
            }
            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        }

        // Save the updated approval request.
        updated.setActive(Boolean.FALSE);
        approvalRequestService.save(updated);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }   
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<ApprovalRequest>> list(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "type", required = false) String type,
            SecurityContextHolderAwareRequestWrapper wrapper) throws NotFoundException {
        logger.info("AdminApi list : state={}, type={}", state, type);
        if (!wrapper.isUserInRole("ROLE_ADMIN")) {
            logger.info("/verdict: User {} is not an admin, returning forbidden", wrapper.getRemoteUser());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<ApprovalRequest> out = FluentIterable
                .from(approvalRequestService.findAll())
                .filter(Predicates.and(new ApprovalStatePredicate(state), new ApprovalTypePredicate(type)))
                .transform(approvalFromDbToJson)
                .toList();
        
        return new ResponseEntity<>(out, HttpStatus.OK);
    }
    
    static class ApprovalStatePredicate implements Predicate<DbApprovalRequest> {
        private final String state;
        private final boolean open;
        
        ApprovalStatePredicate(String state) {
            this.state = state;
            open = "open".equals(state);
        }
        
        @Override
        public boolean apply(DbApprovalRequest a) {
            if (Strings.isNullOrEmpty(state)) {
                return true;
            }
            return a.getActive().booleanValue() == open;
        }
    }

    static class ApprovalTypePredicate implements Predicate<DbApprovalRequest> {
        private final String type;
        
        ApprovalTypePredicate(String type) {
            this.type = type;
        }
        
        @Override
        public boolean apply(DbApprovalRequest a) {
            if (Strings.isNullOrEmpty(type)) {
                return true;
            }
            return a.getApprovalType().equals(type);
        }
    }
}
