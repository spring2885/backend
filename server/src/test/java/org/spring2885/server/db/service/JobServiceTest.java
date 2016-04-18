package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbJob;

import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class JobServiceTest {
	@Mock JobRepository repository;
	private JobService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new JobServiceImpl(repository);

    	// return empty list by default
    	when(repository.existsByTitle(anyString())).thenReturn(Collections.emptyList());
    }
    
    @Ignore("TODO: Jen please fix.")
    @Test
    public void testFindAll() {
    	DbJob p = new DbJob();
    	//when(repository.findAll()).thenReturn(Collections.singleton(p));
    	when(repository.findAllByActiveAndAbuse(anyBoolean(), anyBoolean())).thenReturn(Collections.singleton(p));

    	List<DbJob> persons = Lists.newArrayList(service.findAll());
    	assertEquals(1, persons.size());
    	assertSame(p, persons.get(0));
    }

    @Test
    @Ignore("TODO: Jen please fix.")
    public void testFindAll_none() {
    	//when(repository.findAll()).thenReturn(Collections.emptyList());
    	when(repository.findAllByActiveAndAbuse(anyBoolean(), anyBoolean())).thenReturn(Collections.emptyList());

    	//List<DbJob> persons = Lists.newArrayList(service.findAll(criterias));
    	
    
    }
    
    @Test
    public void testFindByTitle() {
    	DbJob p = new DbJob();
    	p.setTitle("Hello");
    	//p.setjob("Hello");
    	when(repository.findByTitle("Hello")).thenReturn(Collections.singletonList(p));
    	
    	List<DbJob> persons = Lists.newArrayList(service.findByTitle("Hello"));
    	assertEquals(1, persons.size());
    	assertSame(p, persons.get(0));
    }

    @Test
    public void testFindByTitle_None() {
    	List<DbJob> jobs = Lists.newArrayList(service.findByTitle("World"));
    	assertEquals(1, jobs.size());
    }//0

    @Test
    public void testFindById() {
    	DbJob expected = new DbJob();
    	expected.setTitle("Hellow");
    	when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
    	
    	DbJob actual = service.findById(1234);
    	assertSame(expected, actual);
    }
    
    @Test
    public void testFindById_notFound() {
    	DbJob actual = service.findById(1234);
    	assertNull(actual);
    }
    
    @Test
    public void testExistsByTitle() {
    	DbJob p = new DbJob();
    	p.setTitle("Hello");
    	when(repository.findByTitle("Hello")).thenReturn(Collections.singletonList(p));

    	assertTrue(service.existsByTitle("Hello"));
    }
    
    @Test
    public void testExistsByEmail_doesNotExist() {
    	assertFalse(service.existsByTitle("me@"));
    }
    
    @Test
    public void testDelete() {
    	DbJob p = new DbJob();
    	when(repository.findOne(Long.valueOf(21))).thenReturn(p);
    	
    	assertTrue(service.delete(21));
    	verify(repository).delete(Long.valueOf(21));
    }

    @Test
    public void testDelete_notFound() {
    	DbJob p = new DbJob();
    	when(repository.findOne(Long.valueOf(22))).thenReturn(p);
    	
    	assertFalse(service.delete(21));
    	// Since a call to findOne(21) will return null by default, delete should
    	// never be called. Let's verify that.
    	verify(repository, never()).delete(Long.valueOf(21));
    }

    @Test
    public void testSave() {
    	DbJob p = new DbJob();
    	DbJob expected = new DbJob();
    	when(repository.save(same(p))).thenReturn(expected);
    	
    	DbJob actual = service.save(p);
    	assertSame(expected, actual);
    }
}