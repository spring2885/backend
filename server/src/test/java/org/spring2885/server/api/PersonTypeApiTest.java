package org.spring2885.server.api;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
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
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.PersonType;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.person.PersonTypeService;
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
public class PersonTypeApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
    @Autowired private PersonTypeService personTypeService;
    
    private DbPersonType dbPersonType;
    private PersonType personType;
    
    @Before
    public void set() {
    	reset(personTypeService);
    	mockMvc = webAppContextSetup(webappContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
    			.build();
    	
    	dbPersonType = createDbPersonType(1, "PersonType1");
        personType = createPersonType(4, "PersonType2");
    }
    
    static DbPersonType createDbPersonType(long id, String name){
    	DbPersonType pT = new DbPersonType();
    	pT.setId(id);
    	pT.setName(name);
    	return pT;
    }
    
    static PersonType createPersonType(long id, String name){
    	PersonType pT = new PersonType();
    	pT.setId(id);
    	pT.setName(name);
    	return pT;
    }
    
    @Test
    @WithMockUser
    public void testPersonType() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findAll())
    		.thenReturn((Set<DbPersonType>) ImmutableList.of(
    			createDbPersonType(5,  "Title"),
    			createDbPersonType(5,  "Title2")));
    	
    	mockMvc.perform(get("/api/v1/persontype")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].title", Matchers.is("Title")))
    			.andExpect(jsonPath("$[1].title", Matchers.is("Title2")));
    }
    
    /**
     * Tests a {@code /persontype/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testPersonTypeById() throws Exception {
    	// Setup the expectations.
    	DbPersonType p = new DbPersonType();
    	p.setName("ThisTitle");
    	when(personTypeService.findById(21)).thenReturn(p);
    	
    	mockMvc.perform(get("/api/v1/persontype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.title", Matchers.is("ThisTitle")));
    	
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
    	when(personTypeService.findById(21)).thenReturn(null);
    	
    	mockMvc.perform(get("/api/v1/persontype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER","ADMIN"})
    public void testDeletePersonTypeById() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.delete(4)).thenReturn(true);
    	//my mock is going to do this
    	mockMvc.perform(delete("/api/v1/persontype/4")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personTypeService).delete(4);
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findById(4)).thenReturn(dbPersonType);
    	when(personTypeService.findByName("Title"))
    		.thenReturn((DbPersonType) Collections.emptyList());
    	
    	mockMvc.perform(put("/api/v1/persontype/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjectToJsonBytes(personType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(personTypeService, never()).save(Mockito.any(DbPersonType.class));
    }
    
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException  {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
