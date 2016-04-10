package org.spring2885.server.db.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbNewsComment;
import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;

@RunWith(JUnit4.class)
public class NewsCommentServiceTest {
    @Mock NewsCommentRepository repository;

    private NewsCommentService service;
    private DbPerson me;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new NewsCommentServiceImpl(repository);

        me = new DbPerson();
        me.setId(1L);
        me.setEmail("me@");
        DbPersonType student = new DbPersonType(0, "student");
        me.setType(student);
    }
    
    @Test
    public void testFindById() {
        DbNewsComment expected = new DbNewsComment();
        expected.setId(1234L);
        when(repository.findOne(Long.valueOf(1234))).thenReturn(expected);
        
        DbNewsComment actual = service.findById(1234);
        assertSame(expected, actual);
    }
    
    @Test
    public void testFindById_notFound() {
        DbNewsComment actual = service.findById(1234);
        assertNull(actual);
    }
    
    @Test
    public void testDelete() {
        DbNewsComment p = new DbNewsComment();
        when(repository.findOne(Long.valueOf(21))).thenReturn(p);
        
        assertTrue(service.delete(21));
        verify(repository).delete(Long.valueOf(21));
    }

    @Test
    public void testDelete_notFound() {
        DbNewsComment p = new DbNewsComment();
        when(repository.findOne(Long.valueOf(22))).thenReturn(p);
        
        assertFalse(service.delete(21));
        verify(repository, never()).delete(Long.valueOf(21));
    }

    @Test
    public void testSave() {
        DbNewsComment p = new DbNewsComment();
        DbNewsComment expected = new DbNewsComment();
        when(repository.save(same(p))).thenReturn(expected);
        
        DbNewsComment actual = service.save(p);
        assertSame(expected, actual);
    }
    
}
