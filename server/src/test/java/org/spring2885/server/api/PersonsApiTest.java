package org.spring2885.server.api;

import static org.mockito.Matchers.*;
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

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.Person;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.person.LanguageService;
import org.spring2885.server.db.service.person.PersonService;
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
public class PersonsApiTest {
    protected MockMvc mockMvc;
    
    @Autowired protected WebApplicationContext webappContext;
    @Autowired private PersonService personService;
    @Autowired PersonTypeService personTypeService;
    @Autowired LanguageService languageService;
    
    private DbPerson dbMe;
    private Person me;
    private DbPerson otherDbPerson;
    private Person otherPerson;

    @After
    public void after() {
        Mockito.reset(personService, personTypeService, languageService);
    }
    @Before
    public void setup() {
        reset(personService);
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.dispatchOptions(true)
        		.build();
        dbMe = createDbPerson(4, "me@example.com", "aboutMe");
        me = createPerson(4, "me@example.com", "aboutMe");
        otherPerson = createPerson(21, "other@example.com", "user 21");
        otherDbPerson = createDbPerson(21, "other@example.com", "user 21");
        
        // Our tests assume a single default person type of student.
        DbPersonType defaultPersonType = new DbPersonType(0, "student");
        when(personTypeService.defaultType()).thenReturn(defaultPersonType);
        when(personTypeService.findAll()).thenReturn(Collections.singleton(defaultPersonType));
        
        DbLanguage enLanguage = new DbLanguage("en", "English");
        DbLanguage defaultLanguage = new DbLanguage("eo", "Esperanto");
        when(languageService.defaultLanguage()).thenReturn(defaultLanguage);
        when(languageService.findAll()).thenReturn(ImmutableSet.of(enLanguage, defaultLanguage));
    }
    
    static DbPerson createDbPerson(long id, String email, String aboutMe) {
    	DbPerson p = new DbPerson();
    	p.setId(id);
    	p.setEmail(email);
    	p.setAboutMe(aboutMe);
    	p.setLanguage(new DbLanguage("eo", "Esperanto"));
    	return p;
    }
    
    static Person createPerson(long id, String email, String aboutMe) {
    	Person p = new Person();
    	p.setId(id);
    	p.setEmail(email);
    	p.setAboutMe(aboutMe);
    	p.setLang("eo");
    	return p;
    }

    void makeMeFound() {
    	when(personService.findById(4)).thenReturn(dbMe);
    	when(personService.findById(21)).thenReturn(otherDbPerson);
    	when(personService.findByEmail("me@example.com"))
    		.thenReturn(dbMe);
    }
    
    @Test
    @WithMockUser
    public void testPersons() throws Exception {
    	// Setup the expectations.
    	when(personService.findAll())
    		.thenReturn(ImmutableList.of(
    			createDbPerson(5,  "me@example.com", ""),
    			createDbPerson(5,  "me2@example.com", "")));
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(get("/api/v1/profiles")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].email", Matchers.is("me@example.com")))
    			.andExpect(jsonPath("$[1].email", Matchers.is("me2@example.com")));
    }

    @Test
    @WithMockUser
    public void testPersons_q() throws Exception {
        // Setup the expectations.
        when(personService.findAll())
            .thenReturn(ImmutableList.of(
                createDbPerson(5,  "me@example.com", ""),
                createDbPerson(5,  "me2@example.com", "")));
        when(personService.findAll(eq("me2")))
            .thenReturn(ImmutableList.of(
                createDbPerson(5,  "me2@example.com", "")));
        
        mockMvc.perform(get("/api/v1/profiles?q=me2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email", Matchers.is("me2@example.com")));
    }

    @SuppressWarnings("unchecked")
    @Test
    @WithMockUser
    public void testPersons_aq() throws Exception {
        // Setup the expectations.
        when(personService.findAll())
            .thenReturn(ImmutableList.of(
                createDbPerson(5,  "me@example.com", ""),
                createDbPerson(5,  "me2@example.com", "")));
        SearchCriteria expected = new SearchCriteria("email", SearchOperator.EQ, "me2*");
        
        when(personService.findAll(Collections.singletonList(expected)))
            .thenReturn(ImmutableList.of(
                createDbPerson(5,  "me2@example.com", "")));
        
        mockMvc.perform(get("/api/v1/profiles?aq=email:me2*")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email", Matchers.is("me2@example.com")));
    }
    /**
     * Tests a {@code /profiles/:id} where {@code id} is found.
     */
    @Test
    @WithMockUser
    public void testPersonsById() throws Exception {
    	// Setup the expectations.
    	DbPerson p = new DbPerson();
    	p.setEmail("other@example.com");
    	when(personService.findById(21)).thenReturn(p);
    	verifyNoMoreInteractions(personService);
    	
    	mockMvc.perform(get("/api/v1/profiles/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.email", Matchers.is("other@example.com")));
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }

    /**
     * Tests a {@code /profiles/:id} where {@code id} is not found.
     */
    @Test
    @WithMockUser
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
    @WithMockUser(username="me@example.com",roles={"USER","ADMIN"})
    public void testDeletePersonsById() throws Exception {
    	// Setup the expectations.
    	when(personService.findByEmail(eq("me@example.com")))
    		.thenReturn(dbMe);
    	when(personService.delete(4)).thenReturn(true);
    	
    	mockMvc.perform(delete("/api/v1/profiles/4")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personService).delete(4);
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testDelete_anotherPersons_notAdminUser() throws Exception {
    	// Setup the expectations.
    	when(personService.findByEmail(eq("me@example.com")))
    		.thenReturn(dbMe);
    	
    	mockMvc.perform(delete("/api/v1/profiles/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personService, never()).delete(Mockito.anyInt());
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/profiles/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(me))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	verify(personService).save(Mockito.any(DbPerson.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
    	when(personService.findById(4)).thenReturn(dbMe);
    	when(personService.findByEmail("me@example.com"))
    		.thenReturn(null);
    	
    	mockMvc.perform(put("/api/v1/profiles/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(me))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(personService, never()).save(Mockito.any(DbPerson.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut_tryToPutAnother_notAdminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/profiles/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherPerson))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(personService, never()).save(Mockito.any(DbPerson.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_updateAnother_adminUser() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/profiles/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(otherPerson))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	verify(personService).save(Mockito.any(DbPerson.class));
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void testPut_documentNotMatchURL() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/profiles/21")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(createPerson(22, "me@", "")))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isBadRequest());
    	
    	verify(personService, never()).save(Mockito.any(DbPerson.class));
    }    
}
