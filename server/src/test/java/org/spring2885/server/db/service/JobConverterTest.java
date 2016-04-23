package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring2885.model.Job;
import org.spring2885.model.News;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.model.JobConverters.JobsFromDbToJson;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class JobConverterTest {
	public JobConverterTest(){}
	@Autowired JobTypeService jobService;
    @Autowired JobTypeService jobTypeService;
    @Autowired LanguageService languageService;
    @Autowired private JobConverters.JsonToDbConverter jsonToDb;
    @Autowired private JobsFromDbToJson dbToJson;
    @Autowired
    PersonService personService;
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;
    
    private DbPerson dbp = new DbPerson();
    
    @Before
    public void before() {
        reset(languageService, jobService, jobTypeService);
        DbLanguage l1 = new DbLanguage("es", "Spanish");
        when(languageService.findAll()).thenReturn(Collections.singleton(l1));
        
        DbJobType t1 = new DbJobType(1, "teller");
        DbJobType t2 = new DbJobType(1, "engineer");
        when(jobTypeService.findAll()).thenReturn(ImmutableSet.of(t1, t2));
        when(jobTypeService.defaultType()).thenReturn(t1);
    }
    
	@Test
	public void testFromDbToJson(){
	
		DbJob dbp = new DbJob();
		Job p = dbToJson.apply(dbp);
	}
	
}
