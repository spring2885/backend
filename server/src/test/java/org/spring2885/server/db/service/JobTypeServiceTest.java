package org.spring2885.server.db.service;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Contains;
import org.spring2885.server.db.model.DbJobType;
import org.spring2885.server.db.model.DbPersonType;
import org.spring2885.server.db.service.person.PersonTypeRepository;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.spring2885.server.db.service.person.PersonTypeServiceImpl;

import com.google.common.collect.ImmutableList;

@RunWith(JUnit4.class)
public class JobTypeServiceTest {
    @Mock JobTypeRepository repository;
    private JobTypeService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new JobTypeServiceImpl(repository);
    }
    
    @Test
    public void findAll() {
        DbJobType student = new DbJobType(0, "fulltime");
        DbJobType alum = new DbJobType(1, "parttime");
        
        
        
        when(repository.findAll()).thenReturn(ImmutableList.of(student, alum));
        assertThat(service.findAll(), containsInAnyOrder(student, alum));
    }

    @Test
    public void defaultType() {
        DbJobType student = new DbJobType(0, "fulltime");
        
        when(repository.findOne(0L)).thenReturn(student);
        assertEquals(student, service.defaultType());
    }

}
