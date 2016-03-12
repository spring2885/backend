package org.spring2885.server.api;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.Job;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class JobsApiTest {
    protected MockMvc mockMvc;
    
    @Autowired protected WebApplicationContext webappContext;
    @Autowired private JobService jobService;
    @Autowired JobTypeService jobTypeService;
    
    private DbJob dbMe;
    private Job me;
    

    @Before
    public void setup() {
        reset(jobService);
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.build();
        
        dbMe = createDbJob(1, "enginner");
        me = createJob(4, "me@example.com");
        
        
        // Our tests assume a single default person type of student.
        DbJobType defaultPersonType = new DbJobType(0, "student");
        //when(personTypeService.defaultType()).thenReturn(defaultPersonType);
      //  when(personTypeService.findAll()).thenReturn(Collections.singleton(defaultPersonType));
    }
    
    static DbJob createDbJob(long id, String title) {
    	DbJob p = new DbJob();
    	p.setId(id);
    	p.setTitle(title);
    	
    	return p;
    }
    
    static Job createJob(long id, String title) {
    	Job p = new Job();
    	p.setId(id);
    	p.setTitle(title);
    	
    	return p;
    }

    
    
    @Test
    @WithMockUser
    public void testJobs() throws Exception {
    	// Setup the expectations.
    	when(jobService.findAll())
    		.thenReturn(ImmutableList.of(
    			createDbJob(5,  "me@example.com"),
    			createDbJob(5,  "me2@example.com")));
    	
    	
    	mockMvc.perform(get("/api/v1/jobs")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].title", Matchers.is("me@example.com")))
    			.andExpect(jsonPath("$[1].title", Matchers.is("me2@example.com")));
    }


    /**
     * Tests a {@code /profiles/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testJobById() throws Exception {
    	// Setup the expectations.
    	DbJob p = new DbJob();
    	p.setTitle("other@example.com");
    	when(jobService.findById(21)).thenReturn(p);
    	verifyNoMoreInteractions(jobService);
    	
    	mockMvc.perform(get("/api/v1/jobs/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.title", Matchers.is("other@example.com")));
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }

    /**
     * Tests a {@code /profiles/:id} where {@code id} is not found.
     */
    @Test
    @WithMockUser
    public void testJobsById_notFound() throws Exception {
    	// Setup the expectations.
    	when(jobService.findById(21)).thenReturn(null);
    	verifyNoMoreInteractions(jobService);
    	
    	mockMvc.perform(get("/api/v1/jobs/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }


    @Test
    @WithMockUser(username="me@example.com",roles={"USER","ADMIN"})
    public void testDeleteJobsById() throws Exception {
    	// Setup the expectations.
    	when(jobService.delete(4)).thenReturn(true);
    	//my mock is goiing to do thiss
    	mockMvc.perform(delete("/api/v1/jobs/4")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(jobService).delete(4);
    }
    
    
    
   
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(jobService.findById(4)).thenReturn(dbMe);
    	when(jobService.findByTitle("me@example.com"))
    		.thenReturn(Collections.emptyList());
    	
    	mockMvc.perform(put("/api/v1/jobs/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjectToJsonBytes(me))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(jobService, never()).save(Mockito.any(DbJob.class));
    }
    
    
    
    
   
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException  {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }    
}
