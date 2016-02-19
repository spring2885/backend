package org.spring2885.server.db.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.model.Person;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.PersonConverters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class PersonConverterTest {
	public PersonConverterTest(){}
	
	@Test
	public void testFromDbToJson(){
		Function<DbPerson, Person> dtoj = PersonConverters.fromDbToJson();
		DbPerson dbp = new DbPerson();
		Person p = new Person();
		p = dtoj.apply(dbp);
	}
	
	@Test
	public void testFromJsonToDb(){
		Function<Person, DbPerson> jtod = PersonConverters.fromJsonToDb();
		Person p = new Person();
		DbPerson dbp = new DbPerson();
		dbp = jtod.apply(p);
	}
}
