package org.spring2885.server.db.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring2885.model.Person;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.person.LanguageService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class PersonConverterTest {
	public PersonConverterTest(){}
    @Autowired PersonTypeService personService;
    @Autowired PersonTypeService personTypeService;
    @Autowired LanguageService languageService;
    @Autowired private PersonConverters.JsonToDbConverter jsonToDb;
    @Autowired private PersonConverters.FromDbToJson dbToJson;
    
    @Before
    public void before() {
        reset(languageService, personService, personTypeService);
        DbLanguage l1 = new DbLanguage("es", "Spanish");
        when(languageService.findAll()).thenReturn(Collections.singleton(l1));
        
        DbPersonType t1 = new DbPersonType(1, "student");
        DbPersonType t2 = new DbPersonType(1, "faculty");
        when(personTypeService.findAll()).thenReturn(ImmutableSet.of(t1, t2));
        when(personTypeService.defaultType()).thenReturn(t1);
        
        jsonToDb.withDbPerson(new DbPerson());
    }
	
	@Test
	public void testFromDbToJson(){
		DbPerson dbp = new DbPerson();
		dbp.setAboutMe("aboutme");
		Date bday = new Date(System.currentTimeMillis());
		dbp.setBirthdate(bday);
		dbp.setCompanyName("company");
		dbp.setDegreeMajor("CS");
		dbp.setDegreeMinor("math");
		dbp.setDegreeType("BS");
		dbp.setEmail("me@example.com");
		dbp.setFacultyDepartment("CS/MATH");
		dbp.setGraduationYear(2000);
		dbp.setId(100L);
		dbp.setImageURL("http://me.com/me");
		dbp.setLanguage(new DbLanguage("es", "Spanish"));
		dbp.setLastLogon(bday);
		dbp.setName("Someone");
		dbp.setOccupation("Bum");
		dbp.setPhone("555-1212");
		dbp.setResumeURL("linkedin.com/kewldude");
		dbp.setStudentId(12345);
		dbp.setTitle("Master of time and space");
		dbp.setType(new DbPersonType(1, "student"));
		
		Person p = dbToJson.apply(dbp);
		
		assertEquals(dbp.getAboutMe(), p.getAboutMe());
		assertEquals(bday.toString(), p.getBirthdate().toString());
		assertEquals(dbp.getCompanyName(), p.getCompanyName());
		assertEquals(dbp.getDegreeMajor(), p.getDegreeMajor());
		assertEquals(dbp.getDegreeMinor(), p.getDegreeMinor());
		assertEquals(dbp.getDegreeType(), p.getDegreeType());
		assertEquals(dbp.getEmail(), p.getEmail());
		assertEquals(dbp.getFacultyDepartment(), p.getFacultyDepartment());
		assertEquals(dbp.getGraduationYear(), p.getGraduationYear());
		assertEquals(dbp.getImageURL(), p.getImageUrl());
		assertEquals(dbp.getLanguage().getCode(), p.getLang());
		assertEquals(dbp.getLastLogon().toString(), p.getLastLoginDate().toString());
		assertEquals(dbp.getName(), p.getName());
		assertEquals(dbp.getOccupation(), p.getOccupation());
		assertEquals(dbp.getPhone(), p.getPhone());
		assertEquals(dbp.getResumeURL(), p.getResumeUrl());
		assertEquals(dbp.getStudentId(), p.getStudentId());
		assertEquals(dbp.getTitle(), p.getTitle());
		assertEquals(dbp.getType().getName(), p.getVariety());
	}
	
	@Test
	public void testFromJsonToDb() {
        Date bday = new Date(System.currentTimeMillis());

        Person p = new Person();
        p.setAboutMe("aboutMe");
        p.setBirthdate(bday);
        p.setCompanyName("company");
        p.setDegreeMajor("CS");
        p.setDegreeMinor("math");
        p.setDegreeType("BS");
        p.setEmail("me@example.com");
        p.setFacultyDepartment("CS/MATH");
        p.setGraduationYear(2000);
        p.setId(100L);
        p.setImageUrl(("http://me.com/me"));
        p.setLang("es");
        p.setLastLoginDate(bday);
        p.setName("Someone");
        p.setOccupation("Bum");
        p.setPhone("555-1212");
        p.setResumeUrl("linkedin.com/kewldude");
        p.setStudentId(12345);
        p.setTitle("Master of time and space");
        p.setVariety("student");
        
		DbPerson dbp = jsonToDb.apply(p);
		
        assertEquals(dbp.getAboutMe(), p.getAboutMe());
        assertEquals(bday.toString(), p.getBirthdate().toString());
        assertEquals(dbp.getCompanyName(), p.getCompanyName());
        assertEquals(dbp.getDegreeMajor(), p.getDegreeMajor());
        assertEquals(dbp.getDegreeMinor(), p.getDegreeMinor());
        assertEquals(dbp.getDegreeType(), p.getDegreeType());
        assertEquals(dbp.getEmail(), p.getEmail());
        assertEquals(dbp.getFacultyDepartment(), p.getFacultyDepartment());
        assertEquals(dbp.getGraduationYear(), p.getGraduationYear());
        assertEquals(dbp.getImageURL(), p.getImageUrl());
        assertEquals(dbp.getLanguage().getCode(), p.getLang());
        assertEquals(dbp.getLastLogon().toString(), p.getLastLoginDate().toString());
        assertEquals(dbp.getName(), p.getName());
        assertEquals(dbp.getOccupation(), p.getOccupation());
        assertEquals(dbp.getPhone(), p.getPhone());
        assertEquals(dbp.getResumeURL(), p.getResumeUrl());
        assertEquals(dbp.getStudentId(), p.getStudentId());
        assertEquals(dbp.getTitle(), p.getTitle());
        assertEquals(dbp.getType().getName(), p.getVariety());
	}
}
