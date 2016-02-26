package org.spring2885.server.db.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spring2885.server.db.model.DbSocialService;

public class SocialServiceServiceTest {

	@Mock SocialServiceRepository repository;
	private SocialServiceService service;

    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        service = new SocialServiceServiceImpl(repository);

    	// return empty list by default
    	when(repository.findAll()).thenReturn(Collections.emptyList());
    }
    
	@Test
	public void testFindAll() {
		DbSocialService social = new DbSocialService();
		social.setName("linkedin");
		social.setUrl("www.linkedin.com");
		when(repository.findAll()).thenReturn(Collections.singletonList(social));
		
		Set<DbSocialService> actual = service.findAll();
		assertEquals(1, actual.size());
		assertThat(actual, contains(social));
	}

}
