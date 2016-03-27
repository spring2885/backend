package org.spring2885.server.api;

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
import org.spring2885.model.News;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.NewsService;
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
public class NewsApiTest {
    protected MockMvc mockMvc;
    
    @Autowired protected WebApplicationContext webappContext;
    @Autowired private NewsService newsService;
    @Autowired private PersonService personService;
  
    private DbPerson me;
    private DbNews dbNews;
    private News news;
    
    @Before
    public void setup() {
        reset(newsService);
        reset(personService);
        mockMvc = webAppContextSetup(webappContext)
        		.apply(SecurityMockMvcConfigurers.springSecurity())
        		.dispatchOptions(true)
        		.build();
        
        dbNews = createDbNews(4, "TitleNews1");
        news = createNews(4, "TitleNews2");
        me = new DbPerson();
        me.setId(1L);
        me.setEmail("me@");
        dbNews.setPersonId(me);

        // Make the email address "me@" found for user #1.
        when(personService.findById(1)).thenReturn(me);
        when(personService.findByEmail(eq("me@"))).thenReturn(me);
    }
    
    static DbNews createDbNews(long id, String newsTitle) {
    	DbNews n = new DbNews();
    	n.setId(id);
    	n.setTitle(newsTitle);
    	return n;
    }
    
    static News createNews(long id, String newsTitle) {
    	News n = new News();
    	n.setId(id);
    	n.setTitle(newsTitle);
    	return n;
    }
    
    @Test
    @WithMockUser
    public void testNews() throws Exception {
    	// Setup the expectations.
    	when(newsService.findAll(me, true))
    		.thenReturn(ImmutableList.of(
    			createDbNews(5,  "Title"),
    			createDbNews(5,  "Title2")));
    	
    	mockMvc.perform(get("/api/v1/news")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].title", Matchers.is("Title")))
    			.andExpect(jsonPath("$[1].title", Matchers.is("Title2")));
    }
    
    @Test
    @WithMockUser
    public void testNews_q() throws Exception {
        // Setup the expectations.
        when(newsService.findAll(me, true))
            .thenReturn(ImmutableList.of(
                createDbNews(5,  "title1"),
                createDbNews(5,  "title2")));
        when(newsService.findAll(me, true, eq("title2")))
            .thenReturn(ImmutableList.of(
                createDbNews(5,  "title2")));
        
        mockMvc.perform(get("/api/v1/news?q=title2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", Matchers.is("title2")));
    }
    
    @Test
    @WithMockUser
    public void testNews_aq() throws Exception {
        // Setup the expectations.
        when(newsService.findAll(me, true))
            .thenReturn(ImmutableList.of(
                createDbNews(5,  "title1"),
                createDbNews(5,  "title2")));
        SearchCriteria expected = new SearchCriteria("title", SearchOperator.EQ, "title2*");
        
        when(newsService.findAll(me, true, Collections.singletonList(expected)))
            .thenReturn(ImmutableList.of(
                createDbNews(5,  "title2")));
        
        mockMvc.perform(get("/api/v1/news?aq=title:title2*")
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
    public void testNewsById() throws Exception {
    	// Setup the expectations.
    	DbNews p = new DbNews();
    	p.setTitle("ThisTitle");
    	when(newsService.findById(21)).thenReturn(p);
    	
    	mockMvc.perform(get("/api/v1/news/21")
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
    public void testNewsById_notFound() throws Exception {
    	// Setup the expectations.
    	when(newsService.findById(21)).thenReturn(null);
    	
    	mockMvc.perform(get("/api/v1/news/21")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }

    @Test
    @WithMockUser(username="Title",roles={"USER","ADMIN"})
    public void testDeleteNewsById() throws Exception {
    	// Setup the expectations.
        when(newsService.findById(4)).thenReturn(dbNews);
    	when(newsService.delete(4)).thenReturn(true);

    	mockMvc.perform(delete("/api/v1/news/4")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	// Ensure PersonService#delete method was called since the result of our
    	// method is the same no matter what.
    	verify(newsService).delete(4);
    }
    
    @Test
    @WithMockUser(username="Title",roles={"USER"})
    public void testPut() throws Exception {
        // Setup the expectations.
        when(newsService.findById(4)).thenReturn(dbNews);
        
        mockMvc.perform(put("/api/v1/news/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(news))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        
        verify(newsService, never()).save(Mockito.any(DbNews.class));
    }

    @Test
    @WithMockUser(username="other@",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
    	// Setup the expectations.
        when(newsService.findById(4)).thenReturn(dbNews);
    	
    	mockMvc.perform(put("/api/v1/news/4")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjectToJsonBytes(news))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(newsService, never()).save(Mockito.any(DbNews.class));
    }
   
}
