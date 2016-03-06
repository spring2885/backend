package org.spring2885.server.db.service.search;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(JUnit4.class)
public class SearchCriteriaTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(SearchCriteria.class).verify();
    }
    
    @Test
    public void key() {
        SearchCriteria criteria = new SearchCriteria("foo", SearchOperator.EQ, "bar");
        assertEquals("foo", criteria.key());
    }

    @Test
    public void operator() {
        SearchCriteria criteria = new SearchCriteria("foo", SearchOperator.EQ, "bar");
        assertEquals(SearchOperator.EQ, criteria.operator());
    }

    @Test
    public void term() {
        SearchCriteria criteria = new SearchCriteria("foo", SearchOperator.EQ, "bar");
        assertEquals("bar", criteria.term());
    }
    
    @Test
    public void toString_normalCase() {
        SearchCriteria criteria = new SearchCriteria("foo", SearchOperator.EQ, "bar");
        assertThat(criteria.toString(), containsString("foo"));
    }

}
