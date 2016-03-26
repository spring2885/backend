package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Job;
import org.spring2885.model.JobType;
import org.spring2885.model.News;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.model.JobTypeConverters;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.person.PersonService;
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

import com.google.common.collect.FluentIterable;

@RestController
@RequestMapping(value = "/api/v1/jobtype", produces = { APPLICATION_JSON_VALUE })
public class JobTypeApi {
    private static final Logger logger = LoggerFactory.getLogger(JobTypeApi.class);
	
	@Autowired
	private JobTypeService jobTypeService;
	
	@Autowired
    private JobTypeConverters.FromDbToJson jobTypeFromDbToJson; 
     
    @Autowired
    private JobTypeConverters.JsonToDbConverter jobTypeJsonToDb;
	 
    @Autowired
    private RequestHelper requestHelper;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<JobType> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbJobType o = jobTypeService.findById(id);
		if (o == null) {
			// When adding test testJobsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(jobTypeFromDbToJson.apply(o), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> jobsPost(
			
			@RequestBody JobType job
			
			) throws NotFoundException {
		
		DbJobType updatedDbJob = jobTypeJsonToDb.apply(job);
		jobTypeService.save(updatedDbJob);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") Integer id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
		if (!requestHelper.checkAdminRequestIfNeeded(id, request)) {
			String error = 
					"Only admin's can change others... Read this: "
					+ "God, grant me the serenity to accept the things I cannot change,"
					+ "Courage to change the things I can,"
					+ "And wisdom to know the difference.";
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		jobTypeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<JobType>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		
		List<JobType> jobs = FluentIterable.from(jobTypeService.findAll())
				.transform(jobTypeFromDbToJson)
				.toList();
		
		return new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") Integer id,
			@RequestBody JobType jobType,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (!requestHelper.checkAdminRequestIfNeeded(id, request)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (id.intValue() != jobType.getId().intValue()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbJobType db = jobTypeService.findById(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		jobTypeJsonToDb.withDbPersonType(db);
		DbJobType updatedDbJob = jobTypeJsonToDb.apply(jobType);
		jobTypeService.save(updatedDbJob);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}