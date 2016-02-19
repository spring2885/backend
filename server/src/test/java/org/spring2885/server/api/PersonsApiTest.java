package org.spring2885.server.api;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
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
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.build();
    }
    
    @Test
    @WithMockUser
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
    @WithMockUser
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
    	DbPerson p = new DbPerson();
    	p.setEmail("me@example.com");
    	p.setId(4);
    	when(personService.findByEmail(Mockito.anyString()))
    		.thenReturn(Collections.singletonList(p));
    	when(personService.delete(4)).thenReturn(true);
    	
    	mockMvc.perform(delete("/api/v1/profiles/4"))
    			.andExpect(status().isOk());
    	
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(personService).delete(4);
    }
    
    @Configuration
    @EnableWebSecurity
    @EnableWebMvc
    static class TestSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .usernameParameter("user")
                    .passwordParameter("pass")
                    .loginPage("/login")
                 .and().csrf().disable();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER");
        }
    }    
}
