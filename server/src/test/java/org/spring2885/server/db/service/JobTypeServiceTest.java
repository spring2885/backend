package org.spring2885.server.db.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbJobType;

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
