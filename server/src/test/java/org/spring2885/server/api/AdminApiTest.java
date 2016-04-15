package org.spring2885.server.api;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.FacultyRequest;
import org.spring2885.model.Verdict;
import org.spring2885.server.db.model.DbApprovalRequest;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.ApprovalRequestService;
import org.spring2885.server.db.service.ApprovalTypes;
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
public class AdminApiTest {
    protected MockMvc mockMvc;
    private DbPerson me;
    
    @Autowired private WebApplicationContext webappContext;
    @Autowired private ApprovalRequestService approvalRequestService;
    @Autowired private PersonService personService;
    
    @After
    public void after() {
        Mockito.reset(approvalRequestService);
    }
    
    @Before
    public void setup() {
        reset(approvalRequestService);
        mockMvc = webAppContextSetup(webappContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true)
                .build();

        me = new DbPerson();
        me.setId(1L);
        me.setEmail("me@example.com");

        // Make the email address "me@" found for user #1.
        when(personService.findById(1)).thenReturn(me);
        when(personService.findByEmail(eq("me@example.com"))).thenReturn(me);
    }
    
    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void faculty() throws Exception {
        // Setup the expectations.
        
        FacultyRequest req = new FacultyRequest();
        req.setNotes("some-notes");
        
        mockMvc.perform(post("/api/v1/approvals/request/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(req))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(approvalRequestService).save(anyObject());
    }

    @Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void verdict_dontAllowNonAdmins() throws Exception {
        // Setup the expectations.
        
        FacultyRequest req = new FacultyRequest();
        req.setNotes("some-notes");
        
        mockMvc.perform(post("/api/v1/approvals/verdict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(req))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="me@example.com",roles={"USER", "ADMIN"})
    public void verdict() throws Exception {
        // Setup the expectations.
        DbApprovalRequest a = new DbApprovalRequest();
        a.setUuid("some-id");
        a.setApprovalType(ApprovalTypes.FACULTY);
        a.setActive(true);
        a.setItemId(1L);
        when(approvalRequestService.findById("some-id")).thenReturn(a);
        
        Verdict verdict = new Verdict();
        verdict.setApproved(true);
        verdict.setId("some-id");
        verdict.setVerdictNotes("some-notes");
        
        mockMvc.perform(post("/api/v1/approvals/verdict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(verdict))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(approvalRequestService).save(anyObject());
    }

}
