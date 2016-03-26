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
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.PersonType;
import org.spring2885.server.db.model.DbPerson;
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
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class PersonTypeApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
    @Autowired private PersonTypeService personTypeService;
    
    private DbPersonType dbPersonType;
    private PersonType personType;
    private DbPersonType otherDbPersonType;
    private PersonType otherPersonType;
    
    @Before
    public void set() {
    	reset(personTypeService);
    	mockMvc = webAppContextSetup(webappContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
    			.build();
    	
    	dbPersonType = createDbPersonType(1, "PersonType1");
        personType = createPersonType(1, "PersonType1");
        otherDbPersonType = createDbPersonType(21, "PersonType2");
        otherPersonType = createPersonType(21, "PersonType2");
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
    
    void makeMeFound(){
    	when(personTypeService.findById(1)).thenReturn(dbPersonType);
    	when(personTypeService.findById(21)).thenReturn(otherDbPersonType);
    	when(personTypeService.findByName("PersonType1"))
    		.thenReturn(dbPersonType);
    }
    
	@Test
    @WithMockUser
    public void testPersonType() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findAll())
    		.thenReturn(ImmutableSet.of(
    			createDbPersonType(5,  "PersonType"),
    			createDbPersonType(5,  "PersonType2")));
    	verifyNoMoreInteractions(personTypeService);
    	
    	mockMvc.perform(get("/api/v1/persontype")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].name", Matchers.is("PersonType")))
    			.andExpect(jsonPath("$[1].name", Matchers.is("PersonType2")));
    }
    
    /**
     * Tests a {@code /persontype/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testPersonTypeById() throws Exception {
    	// Setup the expectations.
    	DbPersonType p = new DbPersonType();
    	p.setId(21);
    	p.setName("PersonType2");
    	when(personTypeService.findById(21)).thenReturn(p);
    	//when(personTypeService.findAll()).thenReturn(Collections.singletonSet(p));
    	
    	mockMvc.perform(get("/api/v1/persontype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.id", Matchers.is(21)));
    
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    /**
     * Tests a {@code /persontype/:id} where {@code id} is not found.
     */
    @Test
    @WithMockUser
    public void testPersonTypeById_notFound() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findById(21)).thenReturn(null);
    	verifyNoMoreInteractions(personTypeService);
    	
    	mockMvc.perform(get("/api/v1/persontype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER","ADMIN"})
    public void testDeletePersonTypeById() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findById(eq(4)))
			.thenReturn(dbPersonType);
    	when(personTypeService.delete(4)).thenReturn(true);
	
    	mockMvc.perform(delete("/api/v1/persontype/4")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	
    	// Ensure PersonTypeService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personTypeService).delete(4);
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testDelete_anotherPersons_notAdminUser() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findById(eq(21)))
    		.thenReturn(dbPersonType);
    	
    	mockMvc.perform(delete("/api/v1/persontype/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	// Ensure PersonTypeService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personTypeService, never()).delete(Mockito.anyInt());
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/persontype/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(personType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(personTypeService, never()).save(Mockito.any(DbPersonType.class));
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(personTypeService.findById(4)).thenReturn(dbPersonType);
    	when(personTypeService.findByName("PersonType1"))
    		.thenReturn(null);
    	
    	mockMvc.perform(put("/api/v1/persontype/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(dbPersonType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    
    	verify(personTypeService, never()).save(Mockito.any(DbPersonType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com", roles={"USER"})
    public void testPut_tryToPutAnother_notAdminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/persontype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherDbPersonType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(personTypeService, never()).save(Mockito.any(DbPersonType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_updateAnother_adminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/persontype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherPersonType))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	verify(personTypeService).save(Mockito.any(DbPersonType.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_documentNotMatchURL() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/persontype/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(createPersonType(22, "PersonType1234")))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isBadRequest());
    	
    	verify(personTypeService, never()).save(Mockito.any(DbPersonType.class));
    } 
    
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException  {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
