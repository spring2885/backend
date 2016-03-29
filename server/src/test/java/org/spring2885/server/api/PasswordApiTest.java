package org.spring2885.server.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Collections;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.spring2885.model.Reset;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbToken;
import org.spring2885.server.db.service.TokenService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class PasswordApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
	@Autowired private PersonService personService;
    @Autowired private TokenService tokenService;
	
    private DbPerson me;
	private DbToken dbToken;
	private Reset resetToken;
	private DbToken otherDbToken;
	private Reset otherResetToken;
	
	private UUID globalUUID = UUID.randomUUID();
	private UUID globalUUID2 = UUID.randomUUID();
	
	@Before
	public void set() {
		reset(tokenService);
		mockMvc = webAppContextSetup(webappContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
		
		dbToken = createDbToken("me@example.com");
		resetToken = createReset("me@example.com", globalUUID, "newPass");
		otherDbToken = createDbToken("matt@example.com");
		otherResetToken = createReset("matt@example.com", globalUUID2, "newPass");
		
		me = new DbPerson();
		me.setId(1234L);
		me.setEmail("me@example.com");
		me.setName("Me Ra");
	}
	
	static DbToken createDbToken(String email){
		DbToken createdToken1 = new DbToken();
		UUID uuid = UUID.randomUUID();
		createdToken1.setEmail(email);
		createdToken1.setUuid(uuid.toString());
		createdToken1.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
		return createdToken1;
	}
	
	static Reset createReset(String email, UUID token, String newPassword){
		Reset createdReset1 = new Reset();
		createdReset1.setEmail(email);
		createdReset1.setToken(token.toString());
		createdReset1.setNewPassword(newPassword);
		return createdReset1;
	}
	
	void makeMeFound(){
        when(personService.findByEmail("me@example.com")).thenReturn(me);

        when(tokenService.findByEmail("me@example.com")).thenReturn(Collections.singletonList(dbToken));
		when(tokenService.findByEmail("matt@example.com")).thenReturn(Collections.singletonList(otherDbToken));
		when(tokenService.findById(globalUUID.toString())).thenReturn(dbToken);
		when(tokenService.findById(globalUUID2.toString())).thenReturn(otherDbToken);
	}
	
	@Test
	@WithMockUser(username="me@example.com", roles = {"USER", "ADMIN"})
	public void testPostForgot() throws Exception {
		// Setup expectations
		makeMeFound();
		
		mockMvc.perform(post("/auth/forgot")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "me@example.com"))
		        .andDo(print())
				.andExpect(status().isOk());
		
		final ArgumentCaptor<DbToken> captor = ArgumentCaptor.forClass(DbToken.class);
		verify(tokenService).save(captor.capture());
		
		final DbToken actualToken = captor.getValue();
		assertEquals("me@example.com", actualToken.getEmail());
	}
	
	@Test
	@WithMockUser(username="me@example.com", roles = {"USER","ADMIN"})
	public void testPostReset() throws Exception {
		// Setup expectations
		makeMeFound();
		
		mockMvc.perform(post("/auth/reset")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "me@example.com")
                .param("token", dbToken.getUuid())
                .param("newPassword", "welcome"))
		        .andDo(print())
				.andExpect(status().isOk());
	
		verify(tokenService, never()).save(Mockito.any(DbToken.class));
	}
}
