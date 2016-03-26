package org.spring2885.server.api;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.spring2885.server.utils.TestUtils.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.SocialService;
import org.spring2885.server.db.model.DbSocialService;
import org.spring2885.server.db.service.person.SocialServiceService;
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
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class SocialServiceApiTest {
protected MockMvc mockMvc;
    
    @Autowired protected WebApplicationContext webappContext;
    @Autowired private SocialServiceService socialServiceService;
    
    private DbSocialService dbSs;
    private SocialService ss; 
    
    @After
    public void after() {
    	Mockito.reset(socialServiceService);
    }
    
    @Before
    public void setup() {
        reset(socialServiceService);
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.dispatchOptions(true)
        		.build();
        
        dbSs = createDbSocialService("id", "Test.com");
        ss = createSocialService("id2", "Test2.com");
       
    }
    
    static DbSocialService createDbSocialService(String id, String url) {
    	DbSocialService n = new DbSocialService();
    	n.setName(id);
    	n.setUrl(url);
    	return n;
    }
    
    static SocialService createSocialService(String id, String url) {
    	SocialService n = new SocialService();
    	n.setId(id);
    	n.setUrl(url);
    	return n;
    }
    
    void makeMeFound() {
    	when(socialServiceService.findById("id")).thenReturn(dbSs);
    }
	
	@Test
    @WithMockUser
    public void testSocialService() throws Exception {
    	// Setup the expectations.
    	when(socialServiceService.findAll())
    		.thenReturn(ImmutableSet.of(
    			createDbSocialService("id",  "Test.com"),
    			createDbSocialService("id2",  "Test2.com")));
    	verifyNoMoreInteractions(socialServiceService);
    	
    	mockMvc.perform(get("/api/v1/socialservice")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].id", Matchers.is("id")))
    			.andExpect(jsonPath("$[1].id", Matchers.is("id2")));
    }
	
    @Test
    @WithMockUser
    public void testSocialServiceById() throws Exception {
    	// Setup the expectations.
    	DbSocialService p = new DbSocialService();
    	p.setName("id");
    	when(socialServiceService.findById("id")).thenReturn(p);
    	
    	mockMvc.perform(get("/api/v1/socialservice/id")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.id", Matchers.is("id")));
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    @Test
    @WithMockUser
    public void testSocialServiceById_notFound() throws Exception {
    	// Setup the expectations.
    	when(socialServiceService.findById("id")).thenReturn(null);
    	
    	mockMvc.perform(get("/api/v1/socialservice/id")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    @Test
    @WithMockUser(username="id",roles={"USER","ADMIN"})
    public void testDeleteSocialServiceById() throws Exception {
    	// Setup the expectations.
    	when(socialServiceService.delete("id2")).thenReturn(true);
    	//my mock is goiing to do thiss
    	mockMvc.perform(delete("/api/v1/socialservice/id2")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(socialServiceService).delete("id2");
    }
    
    @Test
    @WithMockUser(username="id",roles={"USER"})
    public void testPut() throws Exception {
        // Setup the expectations.
        makeMeFound();
        
        mockMvc.perform(put("/api/v1/socialservice/id2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(ss))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        
        verify(socialServiceService, never()).save(Mockito.any(DbSocialService.class));
    }

    @Test
    @WithMockUser(username="id",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(socialServiceService.findById("id")).thenReturn(dbSs);
    	//don't have a findByUrl method ...not sure if needed
    	/*when(socialServiceService.findByUrl("id"))
    		.thenReturn(Collections.emptyList());*/
    	
    	mockMvc.perform(put("/api/v1/socialservice/id2")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjectToJsonBytes(ss))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(socialServiceService, never()).save(Mockito.any(DbSocialService.class));
    }
    
}
