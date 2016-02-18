package org.spring2885.server.api;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class})
@WebAppConfiguration
public class PersonsApiTest {
    protected MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext webappContext;
    
    @Autowired
    private PersonService personService;
    
    @Before
    public void setup() {
        reset(personService);
        mockMvc = webAppContextSetup(webappContext).build();
    }
    
    @Test
    public void testPersons() throws Exception {
    	// Setup the expectations.
    	List<DbPerson> persons = new ArrayList<>();
    	DbPerson p = new DbPerson();
    	p.setEmail("me@example.com");
    	persons.add(p);
    	DbPerson p2 = new DbPerson();
    	p2.setEmail("me2@example.com");
    	persons.add(p2);
    	when(personService.findAll()).thenReturn(persons);
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(get("/api/v1/profiles")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].email", Matchers.is("me@example.com")))
    			.andExpect(jsonPath("$[1].email", Matchers.is("me2@example.com")));
    }


    /**
     * Tests a {@code /profiles/:id} where {@code id} is found.
     */
    @Test
    public void testPersonsById() throws Exception {
    	// Setup the expectations.
    	DbPerson p = new DbPerson();
    	p.setEmail("me@example.com");
    	when(personService.findById(21)).thenReturn(p);
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(get("/api/v1/profiles/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.email", Matchers.is("me@example.com")));
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }

    /**
     * Tests a {@code /profiles/:id} where {@code id} is not found.
     */
    @Test
    public void testPersonsById_notFound() throws Exception {
    	// Setup the expectations.
    	when(personService.findById(21)).thenReturn(null);
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(get("/api/v1/profiles/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }


    @Test
    public void testDeletePersonsById() throws Exception {
    	// Setup the expectations.
    	when(personService.delete(21)).thenReturn(true);
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(delete("/api/v1/profiles/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personService).delete(21);
    }
}
