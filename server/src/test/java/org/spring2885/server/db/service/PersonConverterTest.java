package org.spring2885.server.db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.spring2885.model.Person;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.PersonConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.base.Function;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersonConverters.class, TestConfig.class })
@WebAppConfiguration
public class PersonConverterTest {
	public PersonConverterTest(){}
	@Autowired
	private PersonConverters.JsonToDbConverter jsonToDb;
	
	@Test
	public void testFromDbToJson(){
		Function<DbPerson, Person> dtoj = PersonConverters.fromDbToJson();
		DbPerson dbp = new DbPerson();
		Person p = new Person();
		p = dtoj.apply(dbp);
	}
	
	@Test
	public void testFromJsonToDb(){
		Person p = new Person();
		DbPerson dbp = new DbPerson();
		dbp = jsonToDb.apply(p);
	}
}
