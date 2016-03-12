package org.spring2885.server.db.service.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(JUnit4.class)
public class SearchOperatorTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(SearchOperator.class).verify();
    }    
   
    @Test
    public void find_found() {
        assertEquals(SearchOperator.EQ, SearchOperator.find(":"));
        assertEquals(SearchOperator.NE, SearchOperator.find("!"));
        assertEquals(SearchOperator.GT, SearchOperator.find(">"));
        assertEquals(SearchOperator.LT, SearchOperator.find("<"));
    }

    @Test
    public void find_empty() {
        assertNull(SearchOperator.find(""));
        assertNull(SearchOperator.find(null));
    }

    @Test
    public void find_notFound() {
        assertNull(SearchOperator.find("$"));  // $ is not found.
    }
}
