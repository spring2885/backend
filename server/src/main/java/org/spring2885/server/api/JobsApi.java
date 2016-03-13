package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Job;
import org.spring2885.model.Person;
import org.spring2885.server.api.exceptions.NotFoundException;
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
import com.google.common.collect.Iterables;

@RestController
@RequestMapping(value = "/api/v1/jobs", produces = { APPLICATION_JSON_VALUE })
public class JobsApi {
	private static final Logger logger = LoggerFactory.getLogger(JobsApi.class);
	
	@Autowired
	private JobService jobService;
	
	 @Autowired
	 private JobConverters.JsonToDbConverter jsonToDbConverter;

	    @Autowired
	    private JobConverters.FromDbToJson dbToJsonConverter;
	    
	    @Autowired
	    private SearchParser searchParser;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Job> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbJob o = jobService.findById(id);
		if (o == null) {
			// When adding test testJobsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dbToJsonConverter.apply(o), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> jobsPost(
			
			
			@RequestBody Job job
			
			) throws NotFoundException {
		
		DbJob db = new DbJob();
		
		DbJob updatedDbJob = jsonToDbConverter.apply(job);
		jobService.save(updatedDbJob);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			String error = 
					"Only admin's can change others... Read this: "
					+ "God, grant me the serenity to accept the things I cannot change,"
					+ "Courage to change the things I can,"
					+ "And wisdom to know the difference.";
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		jobService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Job>> list(
	        @RequestParam(value = "aq", required = false) String aq,
            @RequestParam(value = "q", required = false) String q,
	        @RequestParam(value = "size", required = false) Integer size
	        ) throws NotFoundException {
	    logger.info("JobsApi GET: q={}, aq={}, size={}", q, aq, size);
	    Iterable<DbJob> all;
	    if (!Strings.isNullOrEmpty(q)) {
	        all = jobService.findAll(q);
	    } else if (!Strings.isNullOrEmpty(aq)) {
	        List<SearchCriteria> criterias = searchParser.parse(aq);
            all = jobService.findAll(criterias);
	    } else {
	        all = jobService.findAll();
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
			@RequestBody Job job,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (id.intValue() != job.getId().intValue()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbJob db = jobService.findById(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		jsonToDbConverter.withDbJob(db);
		DbJob updatedDbJob = jsonToDbConverter.apply(job);
		jobService.save(updatedDbJob);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private boolean checkAdminRequestIfNeeded(int requestId, SecurityContextHolderAwareRequestWrapper request) {
		if (!request.isUserInRole("ROLE_ADMIN")) {
			// Only admin's can change other profiles.
			String name = request.getUserPrincipal().getName();
			DbJob me = jobService.findByTitle(name);
			if (me == null) {
				// I can't find myself... need more zen.
				return false;
			}
			
			if (me.getId() != requestId) {
				// Someone is being naughty...
				return false;
			}
		}
		return true;
	}
}

