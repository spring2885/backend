package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbPerson;
import org.springframework.data.jpa.repository.JpaContext;

import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class PersonServiceTest {
	@Mock PersonRepository repository;
	private PersonService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new PersonServiceImpl(repository);

    	// return empty list by default
    	when(repository.findByEmail(anyString())).thenReturn(Collections.emptyList());
    }
    
    @Test
    public void testFindAll() {
    	DbPerson p = new DbPerson();
    	when(repository.findAll()).thenReturn(Collections.singleton(p));

    	List<DbPerson> persons = Lists.newArrayList(service.findAll());
    	assertEquals(1, persons.size());
    	assertSame(p, persons.get(0));
    }

    @Test
    public void testFindAll_none() {
    	when(repository.findAll()).thenReturn(Collections.emptyList());

    	List<DbPerson> persons = Lists.newArrayList(service.findAll());
    	assertEquals(0, persons.size());
    }
    
    @Test
    public void testFindByEmail() {
    	DbPerson p = new DbPerson();
    	p.setEmail("me@");
    	when(repository.findByEmail("me@")).thenReturn(Collections.singletonList(p));
    	
    	List<DbPerson> persons = Lists.newArrayList(service.findByEmail("me@"));
    	assertEquals(1, persons.size());
    	assertSame(p, persons.get(0));
    }

    @Test
    public void testFindByEmail_None() {
    	List<DbPerson> persons = Lists.newArrayList(service.findByEmail("badboy@"));
    	assertEquals(0, persons.size());
    }

    @Test
    public void testFindById() {
    	DbPerson expected = new DbPerson();
    	expected.setEmail("me@");
    	when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
    	
    	DbPerson actual = service.findById(1234);
    	assertSame(expected, actual);
    }
    
    @Test
    public void testFindById_notFound() {
    	DbPerson actual = service.findById(1234);
    	assertNull(actual);
    }
    
    @Test
    public void testExistsByEmail() {
    	DbPerson p = new DbPerson();
    	p.setEmail("me@");
    	when(repository.findByEmail("me@")).thenReturn(Collections.singletonList(p));

    	assertTrue(service.existsByEmail("me@"));
    }
    
    @Test
    public void testExistsByEmail_doesNotExist() {
    	assertFalse(service.existsByEmail("me@"));
    }
    
    @Test
    public void testDelete() {
    	DbPerson p = new DbPerson();
    	when(repository.findOne(Long.valueOf(21))).thenReturn(p);
    	
    	assertTrue(service.delete(21));
    	verify(repository).delete(Long.valueOf(21));
    }

    @Test
    public void testDelete_notFound() {
    	DbPerson p = new DbPerson();
    	when(repository.findOne(Long.valueOf(22))).thenReturn(p);
    	
    	assertFalse(service.delete(21));
    	// Since a call to findOne(21) will return null by default, delete should
    	// never be called. Let's verify that.
    	verify(repository, never()).delete(Long.valueOf(21));
    }

    @Test
    public void testSave() {
    	DbPerson p = new DbPerson();
    	DbPerson expected = new DbPerson();
    	when(repository.save(same(p))).thenReturn(expected);
    	
    	DbPerson actual = service.save(p);
    	assertSame(expected, actual);
    }
}

