package org.spring2885.server.api;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spring2885.server.utils.TestUtils.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.NewsComment;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbNewsComment;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.NewsCommentService;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.person.PersonService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class NewsCommentApiTest {
    protected MockMvc mockMvc;

    @Autowired protected WebApplicationContext webappContext;
    @Autowired private NewsService newsService;
    @Autowired private NewsCommentService newsCommentService;
    @Autowired private PersonService personService;
    
    private DbPerson me;
    private DbNews news;
    private DbNewsComment db;
    private NewsComment comment;

    DbNewsComment createDbNewsComment(long id, String text) {
        DbNewsComment n = new DbNewsComment();
        n.setId(id);
        n.setCommentText(text);
        n.setPerson(me);
        n.setNews(news);
        return n;
    }

    NewsComment createNewsComment(long id, String text) {
        NewsComment n = new NewsComment();
        n.setId(id);
        n.setText(text);
        n.setNewsId(4L);
        return n;
    }

    @Before
    public void setup() {
        reset(newsCommentService);
        reset(newsService);
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
        
        news = new DbNews();
        news.setId(4L);
        news.setTitle("News Title");
        news.setPersonId(me);;
        
        db = createDbNewsComment(4, "TitleNews1");
        comment = createNewsComment(4, "TitleNews2");

        // Make the email address "me@" found for user #1.
        when(personService.findById(1)).thenReturn(me);
        when(personService.findByEmail(eq("me@"))).thenReturn(me);
    }
    
    @Test
    @WithMockUser
    public void testGet() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(5)).thenReturn(createDbNewsComment(5,  "test"));
        
        mockMvc.perform(get("/api/v1/news_comment/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text", Matchers.is("test")));
    }
    
    @Test
    @WithMockUser(username="mess@",roles={"USER"})
    public void testDelete() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(4)).thenReturn(db);
        when(newsCommentService.delete(4)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/news_comment/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(newsCommentService, never()).delete(anyLong());
    }
    
    @Test
    @WithMockUser(username="notfound@",roles={"USER","ADMIN"})
    public void testDelete_admin() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(4)).thenReturn(db);
        when(newsCommentService.delete(4)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/news_comment/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(newsCommentService).delete(4);
    }
    
    @Test
    @WithMockUser(username="me@",roles={"USER"})
    public void testPut() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(4)).thenReturn(db);
        
        mockMvc.perform(put("/api/v1/news_comment/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(newsCommentService).save(Mockito.any(DbNewsComment.class));
    }

    @Test
    @WithMockUser(username="notfound@",roles={"USER"})
    public void testPut_canNotFindMe() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(4)).thenReturn(db);
        
        mockMvc.perform(put("/api/v1/news_comment/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        
        verify(newsCommentService, never()).save(Mockito.any(DbNewsComment.class));
    }
   
    @Test
    @WithMockUser(username="me@",roles={"USER"})
    public void testPost() throws Exception {
        // Setup the expectations.
        when(newsCommentService.findById(4)).thenReturn(db);
        when(newsService.findById(news.getId())).thenReturn(news);
        
        comment.setId(null);
        mockMvc.perform(post("/api/v1/news_comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(newsCommentService).save(Mockito.any(DbNewsComment.class));
    }

}
