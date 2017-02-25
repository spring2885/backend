package org.spring2885.server.db.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring2885.model.Job;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.ConverterUtils;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.model.JobConverters.JobsFromDbToJson;
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
    @Autowired PersonService personService;
    @Autowired private PersonConverters.FromDbToJson personFromDbToJson;
    @Autowired JobConverters.JobsFromDbToJson jobsFromDbToJson;
    @Autowired JobConverters.JsonToDbConverter jobsFromJsonToDb;
    
    private DbPerson dbp = new DbPerson();
    
    @Before
    public void before() {
    	
        Date bday = new Date(System.currentTimeMillis());

        dbp.setId(100L);
        dbp.setAboutMe("aboutme");
        dbp.setCompanyName("company");
        dbp.setDegreeMajor("CS");
        dbp.setDegreeMinor("math");
        dbp.setDegreeType("BS");
        dbp.setEmail("me@example.com");
        dbp.setFacultyDepartment("CS/MATH");
        dbp.setGraduationYear(2000);
        dbp.setImageURL("http://me.com/me");
        dbp.setLanguage(new DbLanguage("es", "Spanish"));
        dbp.setLastLogon(ConverterUtils.asTimestamp(bday));
        dbp.setName("Someone");
        dbp.setOccupation("Bum");
        dbp.setResumeURL("linkedin.com/kewldude");
        dbp.setStudentId(12345);
        dbp.setTitle("Master of time and space");
        dbp.setType(new DbPersonType(1, "student"));

        reset(personService);
        when(personService.findAll()).thenReturn(Collections.singleton(dbp));   
    }
    
	@Test
	public void testFromDbToJson(){
	    // TODO: Add a real test
		Date now = new Date(7L);
		DbJob d = new DbJob();
		d.setCompany("Company");
		d.setDescription("Description");
		d.setId(2L);
		d.setJobType(new DbJobType(1, "teller").getId());
		d.setTitle("Title");
		d.setEndDate(now);
		d.setStartDate(now);
		d.setPostedBy(dbp);
		
		Job j = jobsFromDbToJson.apply(d);
		
		assertEquals(d.getCompany(), j.getCompany());
		assertEquals(d.getDescription(), j.getDescription());
		assertEquals(d.getId(), j.getId());
		assertEquals(d.getJobType(), j.getJobType());
		assertEquals(d.getTitle(), j.getTitle());
		assertEquals(ConverterUtils.asModelDate(d.getEndDate()), j.getEndDate());
		assertEquals(ConverterUtils.asModelDate(d.getStartDate()),j.getStartDate());
		assertEquals(d.getPerson().getEmail(), j.getPostedBy().getEmail());
	}
	
	@Test
	public void testFromJsonToDb(){
		when(personService.findByEmail(eq(dbp.getEmail()))).thenReturn(dbp);
		
		Job j =  new Job();
		DateTime now = new DateTime(7L);
		j.setCompany("Company");
		j.setDescription("Description");
		j.setId(2L);
		j.setJobType(1L);
		j.setTitle("Title");
		j.setEndDate(now);
		j.setStartDate(now);
		j.setPostedBy(personFromDbToJson.apply(dbp));
		
		DbJob d = jobsFromJsonToDb.apply(new DbJob(), j);
		
		assertEquals(d.getCompany(), j.getCompany());
		assertEquals(d.getDescription(), j.getDescription());
		assertEquals(d.getId(), j.getId());
		assertEquals(d.getJobType(), j.getJobType());
		assertEquals(d.getTitle(), j.getTitle());
		assertEquals(ConverterUtils.asModelDate(d.getEndDate()), j.getEndDate());
		assertEquals(ConverterUtils.asModelDate(d.getStartDate()),j.getStartDate());
		assertEquals(d.getPerson().getEmail(), j.getPostedBy().getEmail());
	}
}
