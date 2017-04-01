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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.spring2885.model.Language;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.service.LanguageService;
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
public class LanguageApiTest {
	protected MockMvc mockMvc;
	
	@Autowired protected WebApplicationContext webappContext;
	@Autowired private LanguageService languageService;
	
	private Language myLang;
	private DbLanguage dbLang;
	private Language otherMyLang;
	private DbLanguage otherDbLang;
	
	@Before
	public void setup(){
		reset(languageService);
    	mockMvc = webAppContextSetup(webappContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
    			.build();
        myLang = createLanguage("EX1", "Language1");
        dbLang = createDbLanguage("EX1", "Language1");
        otherMyLang = createLanguage("EX2","Language2");
        otherDbLang = createDbLanguage("Ex2", "Language2");
        
        
	}
	  static Language createLanguage(String code, String desc) {
		  Language l = new Language();
	    	l.setCode(code);
	    	l.setDescription(desc);
	    	return l;
	  }
	  static DbLanguage createDbLanguage(String code, String desc) {
		  DbLanguage dL = new DbLanguage();
		  dL.setCode(code);
		  dL.setDescription(desc);
		  return dL;
	  }
	  void makeMeFound(){
	    	when(languageService.findByCode("EX1")).thenReturn(dbLang);
	    	when(languageService.findByCode("EX2")).thenReturn(otherDbLang);
	  }
	
	@Test
	@WithMockUser
    public void testLangauge() throws Exception {
    	// Setup the expectations.
    	when(languageService.findAll())
    		.thenReturn(ImmutableSet.of(
    			createDbLang("EX2",  "Language #1"),
    			createDbLang("EX2",  "Langauge #2")));
    	
    	mockMvc.perform(get("/api/v1/language")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$[0].description", Matchers.is("Language #1")))
    			.andExpect(jsonPath("$[1].description", Matchers.is("Langauge #2")));
    }

	static DbLanguage createDbLang(String code, String description) {
    	DbLanguage n = new DbLanguage();
    	n.setCode(code);
    	n.setDescription(description);
    	return n;
    }

	@Test
	@WithMockUser
	public void testLanguagesByCode() throws Exception {
		
    	DbLanguage l = new DbLanguage();
    	l.setCode("EXL");
    	l.setDescription("DES");
    	when(languageService.findByCode("EXL")).thenReturn(l);
    	verifyNoMoreInteractions(languageService);
    	
    	mockMvc.perform(get("/api/v1/language/EXL")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.description", Matchers.is("DES")));
    }
	
	@Test
    @WithMockUser(username="me@example.com",roles={"USER"})
    public void testPut() throws Exception {
    	// Setup the expectations.
    	makeMeFound();
    	
    	mockMvc.perform(put("/api/v1/language/EX1")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsBytes(myLang))
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isForbidden());
    	
    	verify(languageService, never()).save(Mockito.any(DbLanguage.class));
    }
}
