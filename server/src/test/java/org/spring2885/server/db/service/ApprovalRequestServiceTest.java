package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbApprovalRequest;

import com.google.common.collect.ImmutableList;

@RunWith(JUnit4.class)
public class ApprovalRequestServiceTest {
    @Mock ApprovalRequestRepository repository;
    private ApprovalRequestService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new ApprovalRequestServiceImpl(repository);
    }
    
    @Test
    public void findAll() {
        DbApprovalRequest a = new DbApprovalRequest();
        List<DbApprovalRequest> all = ImmutableList.of(a);
        when(repository.findAll()).thenReturn(all);
        
        Iterable<DbApprovalRequest> actual = service.findAll();
        assertSame(all, actual);
    }
    
    @Test
    public void findActive() {
        DbApprovalRequest a = new DbApprovalRequest();
        List<DbApprovalRequest> all = ImmutableList.of(a);
        when(repository.findAllByActive(true)).thenReturn(all);

        Iterable<DbApprovalRequest> actual = service.findActive();
        assertSame(all, actual);
    }
    
    @Test
    public void findClosed() {
        DbApprovalRequest a = new DbApprovalRequest();
        List<DbApprovalRequest> all = ImmutableList.of(a);
        when(repository.findAllByActive(false)).thenReturn(all);

        Iterable<DbApprovalRequest> actual = service.findClosed();
        assertSame(all, actual);
    }
    
    @Test
    public void findById() {
        DbApprovalRequest a = new DbApprovalRequest();
        when(repository.findOne(anyString())).thenReturn(a);
        
        DbApprovalRequest actual = service.findById("some-uuid");
        assertEquals(a, actual);
        
        verify(repository).findOne("some-uuid");
    }


    @Test
    public void save() {
        DbApprovalRequest a = new DbApprovalRequest();
        service.save(a);
        
        verify(repository).save(a);
    }
}
