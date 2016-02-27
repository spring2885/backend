package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.spring2885.model.Job;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.service.JobService;
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
import com.google.common.collect.Iterables;

@RestController
@RequestMapping(value = "/api/v1/jobs", produces = { APPLICATION_JSON_VALUE })
public class JobsApi {
	
	@Autowired
	private JobService jobService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Job> get(
			@PathVariable("id") int id) throws NotFoundException {
		DbJob o = jobService.findbyId(id);
		if (o == null) {
			// When adding test testJobsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(JobConverters.fromDbToJson().apply(o), HttpStatus.OK);
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
	public ResponseEntity<List<Job>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		
		List<Job> jobs = FluentIterable.from(jobService.findAll())
				.transform(JobConverters.fromDbToJson())
				.toList();
		
		return new ResponseEntity<>(jobs, HttpStatus.OK);
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
		DbJob db = jobService.findbyId(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		DbJob updatedDbJob = JobConverters.fromJsonToDb(db).apply(job);
		jobService.save(updatedDbJob);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private boolean checkAdminRequestIfNeeded(int requestId, SecurityContextHolderAwareRequestWrapper request) {
		if (!request.isUserInRole("ROLE_ADMIN")) {
			// Only admin's can change other profiles.
			String name = request.getUserPrincipal().getName();
			List<DbJob> possibles = jobService.findbyTitle(name);
			if (possibles.size() != 1) {
				// I can't find myself... need more zen.
				return false;
			}
			
			DbJob me = Iterables.getOnlyElement(possibles);
			if (me.getId() != requestId) {
				// Someone is being naughty...
				return false;
			}
		}
		return true;
	}

}