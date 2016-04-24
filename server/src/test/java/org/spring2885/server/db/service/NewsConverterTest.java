package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring2885.model.News;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.ConverterUtils;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class NewsConverterTest {
    @Autowired
    NewsConverters.NewsFromDbToJson newsFromDbToJson;
    @Autowired
    NewsConverters.JsonToDbConverter newsFromJsonToDb;
    @Autowired
    PersonService personService;
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;
    
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
	public void testFromDbToJson() {
	    Timestamp now = Timestamp.from(Instant.now());
		DbNews d = new DbNews();
        d.setId(4L);
		d.setActive(true);
		d.setDescription("description");
        d.setExpired(now);
        d.setPosted(now);
		d.setPersonId(dbp);
		d.setTitle("This is a title");
		d.setViews(200L);

		News p = newsFromDbToJson.apply(d);
		assertEquals(d.getDescription(), p.getDescription());
		assertEquals(ConverterUtils.asModelDate(d.getExpired()).toString(), p.getExpired().toString());
		assertEquals(d.getPerson().getEmail(), p.getPostedBy().getEmail());
		assertEquals(ConverterUtils.asModelDate(d.getPosted()).toString(), p.getPosted().toString());
		assertEquals(d.getTitle(), p.getTitle());
		assertEquals(d.getViews(), p.getViews());
	}
	
	@Test
	public void testFromJsonToDb(){
        when(personService.findByEmail(eq(dbp.getEmail()))).thenReturn(dbp);

        Date date = new Date(System.currentTimeMillis());
		News p = new News();
        p.setDescription("description");
        p.setExpired(ConverterUtils.asModelDate(date));
        p.setPosted(ConverterUtils.asModelDate(date));
        p.setId(4L);
        p.setPostedBy(personFromDbToJson.apply(dbp));
        p.setTitle("This is a title");
        p.setViews(200L);
        
		DbNews d = newsFromJsonToDb.apply(new DbNews(), p);
		
        assertEquals(d.getDescription(), p.getDescription());
        assertEquals(ConverterUtils.asModelDate(d.getExpired()).toString(), p.getExpired().toString());
        assertEquals(d.getPerson().getEmail(), p.getPostedBy().getEmail());
        assertEquals(ConverterUtils.asModelDate(d.getPosted()).toString(), p.getPosted().toString());
        assertEquals(d.getTitle(), p.getTitle());
        assertEquals(d.getViews(), p.getViews());
	}
}
