package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.spring2885.model.Job;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/jobs", produces = { APPLICATION_JSON_VALUE })
public class JobsApi {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> jobsGet(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		// do some magic!
		List<Job> jobs = new ArrayList<Job>();
		Job job = new Job();
		job.setId(1L);
		job.setJobType("full_time");
		job.setDescription("This is a really good job.");
		jobs.add(job);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Void> jobsPut(@RequestBody Job job) throws NotFoundException {
		// do some magic!
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> jobsPost(@RequestBody Job job) throws NotFoundException {
		// do some magic!
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
