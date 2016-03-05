package org.spring2885.server.db.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchParser {
    private static final Logger logger = LoggerFactory.getLogger(SearchParser.class);

    private final String delims;
    public SearchParser() {
        StringBuilder delims = new StringBuilder(",");
        for (SearchOperator op : SearchOperator.values()) {
            delims.append(op.operator());
        }
        this.delims = delims.toString();
    }
    
    public List<SearchCriteria> parse(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, delims, true);
        List<SearchCriteria> criterias = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            SearchCriteria criteria = nextCriteria(tokenizer);
            if (criteria == null) {
                break;
            }
            criterias.add(criteria);
            if (tokenizer.hasMoreTokens()) {
                // Consume the ',' in between search criteria if one exists.
                String nextToken = tokenizer.nextToken();
                if (!nextToken.equals(",")) {
                    // We're out of sync.
                    logger.warn("We're out of sync on input \"{}\".  Expected \",\" and got \"{}\"", input, nextToken);
                    break;
                }
            }
        }
        return criterias;
    }
    
    private SearchCriteria nextCriteria(StringTokenizer tokenizer) {
        try {
            String key = tokenizer.nextToken();
            SearchOperator op = SearchOperator.find(tokenizer.nextToken());
            String term = tokenizer.nextToken();
            return new SearchCriteria(key, op, term);
        } catch (NoSuchElementException e) {
            // No new criteria.
        }
        return null;
    }
    
}   
