package org.spring2885.server.db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.spring2885.model.Person;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.PersonConverters;

import com.google.common.base.Function;

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
