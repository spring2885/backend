package org.spring2885.server.db.service.search;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(JUnit4.class)
public class SearchParserTest {
    private SearchCriteria name = new SearchCriteria("name", SearchOperator.EQ, "rob*");
    private SearchCriteria graduationYear = new SearchCriteria("graduation_year", SearchOperator.LT, "2015");
    private SearchCriteria occupation = new SearchCriteria("occupation", SearchOperator.NE, "janitor");

    @Test
    public void basic() {
        List<SearchCriteria> actual = new SearchParser().parse("name:rob*,graduation_year<2015,occupation!janitor");
        assertThat(actual, contains(name, graduationYear, occupation));
    }
    
    @Test
    public void multiple() {
        List<SearchCriteria> actual = new SearchParser().parse("");
        assertThat(actual, emptyIterable());
    }

    @Test
    public void malformed_extra() {
        List<SearchCriteria> actual = new SearchParser().parse("name:rob*:");
        assertThat(actual, contains(name));
    }

    @Test
    public void malformed_missing() {
        List<SearchCriteria> actual = new SearchParser().parse("name:");
        assertThat(actual, emptyIterable());
    }

    @Test
    public void single() {
        List<SearchCriteria> actual = new SearchParser().parse("name:rob*");
        assertThat(actual, contains(name));
    }

    @Test
    public void single_trailingComma() {
        List<SearchCriteria> actual = new SearchParser().parse("name:rob*,");
        assertThat(actual, contains(name));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(SearchParser.class).verify();
    }    
}
