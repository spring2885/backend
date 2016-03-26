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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbNews;

import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class NewsServiceTest {
	@Mock NewsRepository repository;
	private NewsService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new NewsServiceImpl(repository);

    	// return empty list by default
    	when(repository.findByTitle(anyString())).thenReturn(Collections.emptyList());
    }
    
    @Test
    public void testFindAll() {
        DbNews p = new DbNews();
        when(repository.findAllByActiveAndAbuse(anyBoolean(), anyBoolean())).thenReturn(Collections.singleton(p));

        List<DbNews> news = Lists.newArrayList(service.findAll());
        assertEquals(1, news.size());
        assertSame(p, news.get(0));
    }

    @Test
    public void testFindAllAdmin() {
        DbNews p = new DbNews();
        when(repository.findAll()).thenReturn(Collections.singleton(p));
        when(repository.findAllByActiveAndAbuse(anyBoolean(), anyBoolean())).thenReturn(Collections.singleton(p));

        List<DbNews> news = Lists.newArrayList(service.findAllAdmin());
        assertEquals(1, news.size());
        assertSame(p, news.get(0));
    }

    @Test
    public void testFindAll_none() {
    	when(repository.findAllByActiveAndAbuse(anyBoolean(), anyBoolean())).thenReturn(Collections.emptyList());

    	List<DbNews> persons = Lists.newArrayList(service.findAll());
    	assertEquals(0, persons.size());
    }
    
    @Test
    public void testFindByEmail() {
    	DbNews p = new DbNews();
    	p.setTitle("Title");
    	when(repository.findByTitle("Title")).thenReturn(Collections.singletonList(p));
    	
    	List<DbNews> persons = Lists.newArrayList(service.findByTitle("Title"));
    	assertEquals(1, persons.size());
    	assertSame(p, persons.get(0));
    }

    @Test
    public void testFindByEmail_None() {
    	List<DbNews> news = Lists.newArrayList(service.findByTitle("BadTitle"));
    	assertEquals(0, news.size());
    }

    @Test
    public void testFindById() {
    	DbNews expected = new DbNews();
    	expected.setTitle("T");
    	when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
    	
    	DbNews actual = service.findById(1234);
    	assertSame(expected, actual);
    }
    
    @Test
    public void testFindById_notFound() {
    	DbNews actual = service.findById(1234);
    	assertNull(actual);
    }
    
    @Test
    public void testExistsByEmail() {
    	DbNews p = new DbNews();
    	p.setTitle("Title");
    	when(repository.findByTitle("Title")).thenReturn(Collections.singletonList(p));

    	assertTrue(service.existsByTitle("Title"));
    }
    
    @Test
    public void testExistsByEmail_doesNotExist() {
    	assertFalse(service.existsByTitle("Title"));
    }
    
    @Test
    public void testDelete() {
    	DbNews p = new DbNews();
    	when(repository.findOne(Long.valueOf(21))).thenReturn(p);
    	
    	assertTrue(service.delete(21));
    	verify(repository).delete(Long.valueOf(21));
    }

    @Test
    public void testDelete_notFound() {
    	DbNews p = new DbNews();
    	when(repository.findOne(Long.valueOf(22))).thenReturn(p);
    	
    	assertFalse(service.delete(21));
    	// Since a call to findOne(21) will return null by default, delete should
    	// never be called. Let's verify that.
    	verify(repository, never()).delete(Long.valueOf(21));
    }

    @Test
    public void testSave() {
    	DbNews p = new DbNews();
    	DbNews expected = new DbNews();
    	when(repository.save(same(p))).thenReturn(expected);
    	
    	DbNews actual = service.save(p);
    	assertSame(expected, actual);
    }
}

