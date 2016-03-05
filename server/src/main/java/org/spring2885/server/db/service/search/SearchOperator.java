package org.spring2885.server.db.service.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SearchOperator {
    
    EQ(':'),
    LT('<'),
    GT('>'),
    NE('!');
    
    private static final Logger logger = LoggerFactory.getLogger(SearchOperator.class);

    private final char operator;
    SearchOperator(char operator) {
        this.operator = operator;
    }
    
    public char operator() {
        return operator;
    }
    /** Finds the search operator or {@code null} on error. */
    public static SearchOperator find(String s) {
        if (s.length() != 1) {
            logger.warn("Invalid SearchOperator {}", s);
            return null;
        }
        char operatorChar = s.charAt(0);
        for (SearchOperator op : SearchOperator.values()) {
            if (op.operator() == operatorChar) {
                return op;
            }
        }
        logger.warn("Unable to find a SearchOperator for {}", s);
        return null;
    }
    
}
