package org.spring2885.server.api;

import static org.mockito.Matchers.eq;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.Job;
import org.spring2885.model.JobType;
import org.spring2885.model.PersonType;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.person.PersonTypeService;
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
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class JobTypeApiTest {
protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
    @Autowired private JobTypeService jobTypeService;
    
    private DbJobType dbJobType;
    private JobType jobType;
    private DbJobType otherDbJobType;
    private JobType otherJobType;
    
    @Before
    public void set() {
    	reset(jobTypeService);
    	mockMvc = webAppContextSetup(webappContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
    			.build();
    	
    	dbJobType = createDbJobType(1, "Type1");
        jobType = createJobType(1, "JobType1");
        otherDbJobType = createDbJobType(21, "PersonType2");
        otherJobType = createJobType(21, "PersonType2");
    }
    
    static DbJobType createDbJobType(long id, String name){
    	DbJobType pT = new DbJobType();
    	pT.setId(id);
    	pT.setName(name);
    	return pT;
    }
    
    static JobType createJobType(long id, String name){
    	JobType pT = new JobType();
    	pT.setId(id);
    	pT.setName(name);
    	return pT;
    }
    
    void makeMeFound(){
    	when(jobTypeService.findById(1)).thenReturn(dbJobType);
    	when(jobTypeService.findById(21)).thenReturn(otherDbJobType);
    	when(jobTypeService.findByName("JobType1"))
    		.thenReturn(dbJobType);
    }
    
	@Test
    @WithMockUser
    public void testJobType() throws Exception {
    	when(jobTypeService.findAll())
    		.thenReturn(ImmutableSet.of(
    			createDbJobType(5,  "JobType"),
    			createDbJobType(5,  "JobType2")));
    	verifyNoMoreInteractions(jobTypeService);
    	
    	mockMvc.perform(get("/api/v1/jobtype")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].name", Matchers.is("JobType")))
    			.andExpect(jsonPath("$[1].name", Matchers.is("JobType2")));
    }
    
    /**
     * Tests a {@code /persontype/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testJobTypeById() throws Exception {
    	// Setup the expectations.
    	DbJobType p = new DbJobType();
    	//p.setId(21);
    	p.setName("ThisTitle");
    	when(jobTypeService.findById(21)).thenReturn(p);
    	verifyNoMoreInteractions(jobTypeService);
    	
    	mockMvc.perform(get("/api/v1/jobtype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.name", Matchers.is("ThisTitle")));
    	//TODO:
    	//Status expected:<200> but was:<404>
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    /**
     * Tests a {@code /persontype/:id} where {@code id} is not found.
     */
    @Test
    @WithMockUser
    public void testNewsById_notFound() throws Exception {
    	// Setup the expectations.
    	when(jobTypeService.findById(21)).thenReturn(null);
    	verifyNoMoreInteractions(jobTypeService);
    	
    	mockMvc.perform(get("/api/v1/jobtype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER","ADMIN"})
    public void testDeleteJobTypeById() throws Exception {
    	// Setup the expectations.
    	when(jobTypeService.findById(eq(4)))
			.thenReturn(dbJobType);
    	when(jobTypeService.delete(4)).thenReturn(true);
	
    	mockMvc.perform(delete("/api/v1/jobtype/4")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	
    	// Ensure PersonTypeService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(jobTypeService).delete(4);
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testDelete_anotherPersons_notAdminUser() throws Exception {
    	// Setup the expectations.
    	when(jobTypeService.findById(eq(21)))
    		.thenReturn(dbJobType);
    	
    	mockMvc.perform(delete("/api/v1/jobtype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(jobTypeService, never()).delete(Mockito.anyInt());
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/jobtype/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(jobType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	//TODO:
    	//Status expected:<403> but was:<400>
    	
    	verify(jobTypeService, never()).save(Mockito.any(DbJobType.class));
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(jobTypeService.findById(4)).thenReturn(dbJobType);
    	when(jobTypeService.findByName("Title"))
    		.thenReturn(null);
    	
    	mockMvc.perform(put("/api/v1/jobtype/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(dbJobType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	//TODO:
    	//Status expected:<403> but was:<400>
    
    	verify(jobTypeService, never()).save(Mockito.any(DbJobType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com", roles={"USER"})
    public void testPut_tryToPutAnother_notAdminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/jobtype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherDbJobType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	//TODO:
    	//Status expected:<403> but was:<200>
    	
    	verify(jobTypeService, never()).save(Mockito.any(DbJobType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_updateAnother_adminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/jobtype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherJobType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	verify(jobTypeService).save(Mockito.any(DbJobType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_documentNotMatchURL() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/jobtype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(createJobType(22, "PersonType1234")))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isBadRequest());
    	
    	verify(jobTypeService, never()).save(Mockito.any(DbJobType.class));
    } 
    
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException  {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}