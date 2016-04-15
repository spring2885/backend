package org.spring2885.server.db.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.same;
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
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.person.PersonTypeRepository;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.spring2885.server.db.service.person.PersonTypeServiceImpl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class PersonTypeServiceTest {
    @Mock PersonTypeRepository repository;
    private PersonTypeService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new PersonTypeServiceImpl(repository);
    }
    
    @Test
    public void findAll() {
        DbPersonType student = new DbPersonType(0, "student");
        DbPersonType alum = new DbPersonType(1, "alumni");
        
        when(repository.findAll()).thenReturn(ImmutableList.of(student, alum));
        assertThat(service.findAll(), containsInAnyOrder(student, alum));
    }

    @Test
    public void defaultType() {
        DbPersonType student = new DbPersonType(1, "student");
        
        when(repository.findOne(1L)).thenReturn(student);
        assertEquals(student, service.defaultType());
    }

    @Test
    public void findById(){
    	DbPersonType expected = new DbPersonType();
    	expected.setName("student");
    	when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
    	
    	DbPersonType actual = service.findById(1234);
    	assertSame(expected, actual);
    }
    
    @Test
    public void findByName() {
    	DbPersonType pt = new DbPersonType(0, "student");
    	when(repository.findByName("student")).thenReturn(Collections.singletonList(pt));
    	
    	List<DbPersonType> personTypes = Lists.newArrayList(service.findByName("student"));
    	assertEquals(1, personTypes.size());
    	assertSame(pt, personTypes.get(0));
    }
    
    @Test
    public void delete() {
    	DbPersonType pt = new DbPersonType();
    	when(repository.findOne(Long.valueOf(21))).thenReturn(pt);
    	
    	assertTrue(service.delete(21));
    	verify(repository).delete(Long.valueOf(21));
    }
    
    @Test
    public void save() {
    	DbPersonType pt = new DbPersonType();
    	DbPersonType expected = new DbPersonType();
    	when(repository.save(same(pt))).thenReturn(expected);
    	
    	DbPersonType actual = service.save(pt);
    	assertSame(expected, actual);
    }
    
    @Test
    public void existsByName() {
    	DbPersonType pt = new DbPersonType();
    	pt.setName("student");
    	when(repository.findByName("student")).thenReturn(Collections.singletonList(pt));
    	
    	assertTrue(service.existsByName("student"));
    }
}
