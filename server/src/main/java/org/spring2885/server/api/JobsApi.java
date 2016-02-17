package org.spring2885.server.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.spring2885.model.Job;
import org.springframework.core.NestedCheckedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/api/jobs", produces = { APPLICATION_JSON_VALUE })
public class JobsApi 
{
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ResponseEntity<JobsApi> getParameters(@PathVariable ("id") int id) throws NestedCheckedException {
		if (id<=100)
		{
			JobsApi job = new JobsApi(); //pass parmameters?
			return new ResponseEntity<JobsApi>(job,HttpStatus.OK);
			
					
		}
		
		return new ResponseEntity<JobsApi>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<List> jobsGet(@RequestBody JobsApi job) throws NestedCheckedException {
		// do some magic!
		List<Job> jobs = new ArrayList<Job>();
		Job job1 = new Job();
		job1.setId(1L);
		job1.setJobType("full_time");
		job1.setDescription("This is a really good job.");
		jobs.add(job1);
		return new ResponseEntity<List>(HttpStatus.OK);
	}
	
	
	
	/*
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/jobs", method = RequestMethod.POST)
	public ResponseEntity<String> createJob(@RequestBody JobsApi job) 
	{
	    System.out.println(job);
	    return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/jobs/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JobsApi> updateJob(@PathVariable("id") int id, @RequestBody JobsApi job) 
	{
	    System.out.println(id);
	    System.out.println(job);
	    return new ResponseEntity<JobsApi>(job, HttpStatus.OK);
	    //do we put code here?
	    
	}
	
	
	@RequestMapping(value = "/jobs/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> updateJob(@PathVariable("id") int id) 
	{
	    System.out.println(id);
	    return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
}
