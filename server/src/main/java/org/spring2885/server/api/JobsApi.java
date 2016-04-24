package org.spring2885.server.api;

import static com.google.common.base.Preconditions.checkState;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Job;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.service.JobService;
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
@RequestMapping(value = "/api/v1/jobs", produces = { APPLICATION_JSON_VALUE })
public class JobsApi {
	private static final Logger logger = LoggerFactory.getLogger(JobsApi.class);
	
    @Autowired
    private JobService jobService;
    
    @Autowired
    private JobConverters.JsonToDbConverter jobsJsonToDb;
    
    @Autowired
    private JobConverters.JobsFromDbToJson dbToJsonConverter;
    
    @Autowired
    private SearchParser searchParser;
    
    @Autowired
    private RequestHelper requestHelper;
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Job> get(
			@PathVariable("id") int id) throws NotFoundException {
        logger.info("GET /api/v1/jobs/{}", id);
		DbJob o = jobService.findById(id);
		if (o == null) {
			// When adding test testPersonsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dbToJsonConverter.apply(o), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
        logger.info("DELETE /api/v1/jobs/{}", id);
        DbJob db = jobService.findById(id);
        if (db == null) {
            logger.info("DELETE /api/v1/jobs/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            logger.info("DELETE /api/v1/jobs/{} FORBIDDEN", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

		jobService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Job>> list(
			@RequestParam(value = "aq", required = false) String aq,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "admin", required = false, defaultValue="false") boolean adminRequest,
	        @RequestParam(value = "size", required = false) Integer size,
	        SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
        logger.info("GET /api/v1/jobs: q={}, aq={}, size={}", q, aq, size);
		
		if (adminRequest && !requestHelper.isAdminRequest(request)) {
	        logger.info("GET /api/v1/jobs: FORBIDDEN");
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		DbPerson me = requestHelper.loggedInUser(request);
		
	    Iterable<DbJob> all;
	    if (!Strings.isNullOrEmpty(q)) {
	        all = jobService.findAll(me, adminRequest, q);
	    } else if (!Strings.isNullOrEmpty(aq)) {
	        List<SearchCriteria> criterias = searchParser.parse(aq);
            all = jobService.findAll(me, adminRequest, criterias);
	    } else {
	        all = jobService.findAll(me, adminRequest);
	    }
		
	    for (DbJob n : all) {
	        logger.trace("news={}", n.toString());
	    }
	    
		FluentIterable<Job> iterable = FluentIterable.from(all)
				.transform(dbToJsonConverter);
		// Support size parameter, but only if it's set (and not 0)
		if (size != null && size.intValue() > 0) {
		    iterable.limit(size);
		}
		return new ResponseEntity<>(iterable.toList(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") Integer id,
			@RequestBody Job jobs,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
        logger.info("PUT /api/v1/jobs/{}", id);
        
		if (id.longValue() != jobs.getId().longValue()) {
	        logger.info("PUT /api/v1/jobs/{} BAD REQUEST {} != {} ", id, id, jobs.getId());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

        DbJob db = jobService.findById(id);
        if (db == null) {
            logger.info("PUT /api/v1/jobs/{} NOT FOUND", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!requestHelper.checkAdminRequestIfNeeded(db.getPerson().getId(), request)) {
            logger.info("PUT /api/v1/jobs/{} FORBIDDEN", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
		
		DbJob updatedDbNews = jobsJsonToDb.apply(db, jobs);
		jobService.save(updatedDbNews);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> post(
			@RequestBody Job jobs,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {

        logger.info("POST /api/v1/jobs: {}", jobs);
	    // Look up the currently logged in user
        DbPerson me = requestHelper.loggedInUser(request);
        checkState(me != null);

	    // Create our DbNews version
	    DbJob db = jobsJsonToDb.apply(new DbJob(), jobs);
        // Since we are doing a post, set defaults.
	    db.setId(null);
	    db.setPostedBy(me);

		jobService.save(db);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
