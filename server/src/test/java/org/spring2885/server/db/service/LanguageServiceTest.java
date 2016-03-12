package org.spring2885.server.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbLanguage;
import org.spring2885.server.db.service.person.LanguageRepository;
import org.spring2885.server.db.service.person.LanguageService;
import org.spring2885.server.db.service.person.LanguageServiceImpl;

import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class LanguageServiceTest {

    @Mock LanguageRepository repository;
    private LanguageService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new LanguageServiceImpl(repository);
    }
    
    @Test
    public void testFindAll() {
        DbLanguage l = new DbLanguage();
        when(repository.findAll()).thenReturn(Collections.singleton(l));

        List<DbLanguage> persons = Lists.newArrayList(service.findAll());
        assertEquals(1, persons.size());
        assertSame(l, persons.get(0));
    }

    @Test
    public void testFindAll_none() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<DbLanguage> persons = Lists.newArrayList(service.findAll());
        assertEquals(0, persons.size());
    }
    
    @Test
    public void testDefault() {
        DbLanguage en = new DbLanguage();
        when(repository.findOne("en")).thenReturn(en);
        
        DbLanguage actual = service.defaultLanguage();
        assertSame(en, actual);
        
    }
}
