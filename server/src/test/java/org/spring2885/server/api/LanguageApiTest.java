package org.spring2885.server.api;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.spring2885.model.Language;
import org.spring2885.model.News;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.LanguageServiceTest;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableList;

public class LanguageApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
	@Autowired private PersonService LanguageService;
	
	// private DbLanguage dbMy;
	 //private Language my;
	
	  @Test
	  //@WithMockLang
	    public void testLangauge() throws Exception {
	    	// Setup the expectations.
	    	when(LanguageServiceTest.testFindAll())
	    		.thenReturn(ImmutableList.of(
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
    	// Setup the expectations.
    	DbLanguage l = new DbLanguage();
    	l.setDescription("EXL");
    	when(LanguageService.findByCode("EXL")).thenReturn(l);
    	verifyNoMoreInteractions(LanguageService);
    	
    	mockMvc.perform(get("/api/v1/profiles/EXL")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.code", Matchers.is("EXL")));
    	
    	// N.B: We don't have to verify anything here since we're asserting
    	// the results that were setup by PersonService.
    }
}
