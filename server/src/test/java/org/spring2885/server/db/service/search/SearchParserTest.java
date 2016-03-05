package org.spring2885.server.db.service.search;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SearchParserTest {
    @Test
    public void basic() {
        SearchCriteria name = new SearchCriteria("name", SearchOperator.EQ, "rob*");
        SearchCriteria graduationYear = new SearchCriteria("graduation_year", SearchOperator.LT, "2015");
        SearchCriteria occupation = new SearchCriteria("occupation", SearchOperator.NE, "janitor");
        
        List<SearchCriteria> actual = new SearchParser().parse("name:rob*,graduation_year<2015,occupation!janitor");
        
        assertThat(actual, contains(name, graduationYear, occupation));
    }
}
