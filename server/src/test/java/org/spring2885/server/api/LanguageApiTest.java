package org.spring2885.server.api;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.spring2885.model.Language;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

public class LanguageApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
	@Autowired private LanguageService LanguageService;
	
	private DbLanguage dbMy;
	private Language myLang;
	
	public void set(){
		reset(LanguageService);
    	mockMvc = webAppContextSetup(webappContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
    			.build();
		dbMy = createDbLang("EX1", "Language1");
        myLang = createLanguage("EX1", "Language1");
	}
	  static Language createLanguage(String code, String desc) {
		  Language l = new Language();
	    	l.setCode(code);
	    	l.setDescription(desc);
	    	return l;
	}

	@Ignore("Dan: Please fix and then remove this annotation")
	@SuppressWarnings("unchecked")
	@Test
	  //@WithMockLang
	    public void testLangauge() throws Exception {
	    	// Setup the expectations.
	    	when(LanguageService.findAll())
	    		.thenReturn((Set<DbLanguage>) ImmutableList.of(
	    			createDbLang("EX2",  "Language #1"),
	    			createDbLang("EX2",  "Langauge #2")));
	    	
	    	mockMvc.perform(get("/api/v1/Language")
	    			.accept(MediaType.APPLICATION_JSON))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    			.andExpect(jsonPath("$[0].title", Matchers.is("Language #1")))
	    			.andExpect(jsonPath("$[1].title", Matchers.is("Language #2")));
	    }

	static DbLanguage createDbLang(String code, String description) {
    	DbLanguage n = new DbLanguage();
    	n.setCode(code);
    	n.setDescription(description);
    	return n;
    }
	public void testLanguagesByCode() throws Exception {
    	DbLanguage l = new DbLanguage();
    	l.setDescription("EXL");
    	when(LanguageService.findByCode("EXL")).thenReturn(l);
    	verifyNoMoreInteractions(LanguageService);
    	
    	mockMvc.perform(get("/api/v1/profiles/EXL")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.code", Matchers.is("EXL")));
    }
	
	@Ignore("Dan: Please fix and then remove this annotation")
	@Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/language/EX2")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(myLang))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(LanguageService, never()).save(Mockito.any(DbLanguage.class));
    }

	private void makeMeFound() {
		// TODO Auto-generated method stub
		
	}
}
