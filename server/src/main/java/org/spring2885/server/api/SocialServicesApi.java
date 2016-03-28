package org.spring2885.server.api;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.SocialService;
import org.spring2885.server.api.exceptions.NotFoundException;
import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.db.model.DbSocialService;
import org.spring2885.server.db.model.SocialServiceConverters;
import org.spring2885.server.db.service.person.SocialServiceService;
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
@RequestMapping(value = "/api/v1/socialservice", produces = { APPLICATION_JSON_VALUE })
public class SocialServicesApi {
	private static final Logger logger = LoggerFactory.getLogger(SocialServicesApi.class);
	
	@Autowired
	private SocialServiceService socialServiceService;
	
	@Autowired
	private SocialServiceConverters.JsonToDbConverter socialServiceJsonToDb;
	
	@Autowired
	private SocialServiceConverters.SocialServiceFromDbToJson dbToJsonConverter;
	
    @Autowired
    private RequestHelper requestHelper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SocialService> get(
			@PathVariable("id") String id) throws NotFoundException {
		DbSocialService o = socialServiceService.findById(id);
		if (o == null) {
			// When adding test testJobsById_notFound, was getting a NullPointerException
			// here, so needed to add this.
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dbToJsonConverter.apply(o), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("id") String id,
			SecurityContextHolderAwareRequestWrapper request)
			throws NotFoundException {
		
		if (!requestHelper.isAdminRequest(request)) {
			String error = 
					"Only admin's can change others... Read this: "
					+ "God, grant me the serenity to accept the things I cannot change,"
					+ "Courage to change the things I can,"
					+ "And wisdom to know the difference.";
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		socialServiceService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SocialService>> list(@RequestParam(value = "size", required = false) Double size)
			throws NotFoundException {
		
		List<SocialService> ss = FluentIterable.from(socialServiceService.findAll())
				.transform(dbToJsonConverter)
				.toList();
		
		return new ResponseEntity<>(ss, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(
			@PathVariable("id") String id,
			@RequestBody SocialService ss,
			SecurityContextHolderAwareRequestWrapper request) throws NotFoundException {
		
		if (!checkAdminRequestIfNeeded(id, request)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		if (!id.equals(ss.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DbSocialService db = socialServiceService.findById(id);
		if (db == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		socialServiceJsonToDb.withDbSocialService(db);
		DbSocialService updatedDbSocialService = socialServiceJsonToDb.apply(ss);
		socialServiceService.save(updatedDbSocialService);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> newsPost(
			
			
			@RequestBody SocialService ss
			
			) throws NotFoundException {
		
		DbSocialService db = new DbSocialService();
		
		DbSocialService updatedDbSocialService = socialServiceJsonToDb.apply(ss);
		socialServiceService.save(updatedDbSocialService);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
    
	
	
	private boolean checkAdminRequestIfNeeded(String requestId, SecurityContextHolderAwareRequestWrapper request) {
		if (!request.isUserInRole("ROLE_ADMIN")) {
			// Only admin's can change other profiles.
			String name = request.getUserPrincipal().getName();
			DbSocialService me = socialServiceService.findById(name);
			if (me == null) {
				// I can't find myself... need more zen.
				return false;
			}
			
			if (!me.getName().equals(requestId)) {
				// Someone is being naughty...
				return false;
			}
		}
		return true;
	}
	
	

}
