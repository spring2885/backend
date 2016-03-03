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
import org.spring2885.server.db.model.DbToken;
import org.springframework.data.jpa.repository.JpaContext;

import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class TokenServiceTest {
	@Mock TokenRepository repository;
	private TokenService service;
	
	@Before public void initMocks(){
		MockitoAnnotations.initMocks(this);
		service = new TokenServiceImpl(repository);
		
		//return empty list by default
		when(repository.findByEmail(anyString())).thenReturn(Collections.emptyList());
	}
	
	@Test
	public void testFindAll(){
		DbToken t = new DbToken();
		when(repository.findAll()).thenReturn(Collections.singleton(t));

		List<DbToken> tokens = Lists.newArrayList(service.findAll());
		assertEquals(1, tokens.size());
		assertSame(t, tokens.get(0));
	}
	
	@Test
	public void testFindAll_none() {
		when(repository.findAll()).thenReturn(Collections.emptyList());
		List<DbToken> tokens = Lists.newArrayList(service.findAll());
		assertEquals(0, tokens.size());
	}
	
	@Test
	public void testFindByEmail() {
		DbToken t = new DbToken();
		t.setEmail("me@");
		when(repository.findByEmail("me@")).thenReturn(Collections.singletonList(t));
    	
		List<DbToken> tokens = Lists.newArrayList(service.findByEmail("me@"));
		assertEquals(1, tokens.size());
		assertSame(t, tokens.get(0));
	}
	
	@Test
	public void testFindByEmail_None() {
		List<DbToken> tokens = Lists.newArrayList(service.findByEmail("badboy@"));
		assertEquals(0, tokens.size());
	}
	
	@Test
    public void testFindById() {
		DbToken expected = new DbToken();
		expected.setEmail("me@");
		when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
		
		DbToken actual = service.findById(1234);
		assertSame(expected, actual);
    }
	
	@Test
    public void testFindById_notFound() {
    	DbToken actual = service.findById(1234);
    	assertNull(actual);
    }
	
	@Test
	public void testExistsByEmail() {
		DbToken t = new DbToken();
		t.setEmail("me@");
		
		when(repository.findByEmail("me@")).thenReturn(Collections.singletonList(t));
		assertTrue(service.existsByEmail("me@"));
	}
	
	@Test
	public void testExistsByEmail_doesNotExist() {
		assertFalse(service.existsByEmail("me@"));
	}
	
	@Test
	public void testDelete() {
		DbToken t = new DbToken();
		when(repository.findOne(Long.valueOf(21))).thenReturn(t);
    	
		assertTrue(service.delete(21));
		verify(repository).delete(Long.valueOf(21));
	}
	
	@Test
	public void testDelete_notFound() {
		DbToken t = new DbToken();
		when(repository.findOne(Long.valueOf(22))).thenReturn(t);
    	
		assertFalse(service.delete(21));
		// Since a call to findOne(21) will return null by default, delete should
		// never be called. Let's verify that.
		verify(repository, never()).delete(Long.valueOf(21));
    }
	
	@Test
	public void testSave() {
		DbToken t = new DbToken();
		DbToken expected = new DbToken();
		when(repository.save(same(t))).thenReturn(expected);
    	
		DbToken actual = service.save(t);
		assertSame(expected, actual);
	}
	
	/**
	 * test deleteByEmail(String email);
	 */
	@Test
	public void testDeleteByEmail(){
		DbToken t = new DbToken();
		when(repository.deleteByEmail(t.getEmail())).thenReturn((List<DbToken>) t);
		
		//assertTrue(service.deleteByEmail("matt@spring2885.org"));
		verify(repository).deleteByEmail("matt@spring2885.org");
	} 
}
