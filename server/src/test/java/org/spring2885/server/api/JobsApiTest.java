package org.spring2885.server.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spring2885.server.utils.TestUtils.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.Job;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.search.SearchCriteria;
import org.spring2885.server.db.service.search.SearchOperator;
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
    @Autowired private PersonService personService;
  
    private DbPerson me;
    private DbJob dbJobs;
    private Job jobs;
    
    @Before
    public void setup() {
        reset(jobService);
        reset(personService);
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.dispatchOptions(true)
        		.build();
        
        me = new DbPerson();
        me.setId(1L);
        me.setEmail("me@");
        DbPersonType student = new DbPersonType(0, "student");
        me.setType(student);
        
        dbJobs = createDbJob(4, "TitleNews1");
        jobs = createJob(4, "TitleNews2");

        // Make the email address "me@" found for user #1.
        when(personService.findById(1)).thenReturn(me);
        when(personService.findByEmail(eq("me@"))).thenReturn(me);
    }
    
    DbJob createDbJob(long id, String newsTitle) {
    	DbJob n = new DbJob();
    	n.setId(id);
    	n.setTitle(newsTitle);
    	
    	n.setPostedbypersonId(me);
    	return n;
    }
    
    Job createJob(long id, String newsTitle) {
    	Job n = new Job();
    	n.setId(id);
    	n.setTitle(newsTitle);
    	return n;
    }
    
    @Test
    @WithMockUser
    public void testJobs() throws Exception {
    	// Setup the expectations.
    	when(jobService.findAll(any(DbPerson.class), eq(false)))
    		.thenReturn(ImmutableList.of(
    			createDbJob(5,  "Title"),
    			createDbJob(5,  "Title2")));
    	
    	mockMvc.perform(get("/api/v1/jobs")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].title", Matchers.is("Title")))
    			.andExpect(jsonPath("$[1].title", Matchers.is("Title2")));
    }
    
    @Test
    @WithMockUser
    public void testJobs_q() throws Exception {
        // Setup the expectations.
        when(jobService.findAll(any(DbPerson.class), eq(false), eq("title2")))
            .thenReturn(ImmutableList.of(
                createDbJob(5,  "title2")));
        
        mockMvc.perform(get("/api/v1/jobs?q=title2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", Matchers.is("title2")));
    }
    
    @Test
    @WithMockUser
    public void testJobs_aq() throws Exception {
        // Setup the expectations.
        SearchCriteria expected = new SearchCriteria("title", SearchOperator.EQ, "title2*");
        when(jobService.findAll(any(DbPerson.class), eq(false), eq(Collections.singletonList(expected))))
            .thenReturn(ImmutableList.of(
                createDbJob(5,  "title2")));
        
        mockMvc.perform(get("/api/v1/jobs?aq=title:title2*")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", Matchers.is("title2")));
    }

    /**
     * Tests a {@code /news/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testJobsById() throws Exception {
    	// Setup the expectations.
    	DbJob p = new DbJob();
    	p.setTitle("ThisTitle");
    	p.setPostedbypersonId(me);
    	when(jobService.findById(21)).thenReturn(p);
    	
    	mockMvc.perform(get("/api/v1/jobs/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.title", Matchers.is("ThisTitle")));
    	
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
    	
    	mockMvc.perform(get("/api/v1/jobs/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }

    @Test
    @WithMockUser(username="Title",roles={"USER","ADMIN"})
    public void testDeleteJobsById() throws Exception {
    	// Setup the expectations.
        when(jobService.findById(4)).thenReturn(dbJobs);
    	when(jobService.delete(4)).thenReturn(true);

    	mockMvc.perform(delete("/api/v1/jobs/4")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(jobService).delete(4);
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER"})
    public void testPut() throws Exception {
        // Setup the expectations.
        when(jobService.findById(4)).thenReturn(dbJobs);
        
        mockMvc.perform(put("/api/v1/jobs/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(jobs))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        
        verify(jobService, never()).save(Mockito.any(DbJob.class));
    }

    @Test
    @WithMockUser(username="other@",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
        when(jobService.findById(4)).thenReturn(dbJobs);
    	
    	mockMvc.perform(put("/api/v1/jobs/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjectToJsonBytes(jobs))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(jobService, never()).save(Mockito.any(DbJob.class));
    }
   
}
