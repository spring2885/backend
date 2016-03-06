package org.spring2885.server.db.service.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class SearchCriteriaSpecification<T> implements Specification<T> {
    private static final Logger logger = LoggerFactory.getLogger(SearchCriteriaSpecification.class);
    
    private final SearchCriteria criteria;
    public SearchCriteriaSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (criteria.operator()) {
        case EQ: {
            if (criteria.term().contains("*")) {
                // This will use 'LIKE' not equal.
                return builder.like(root.get(criteria.key()), criteria.term().replace('*', '%'));
            }
            return builder.equal(root.get(criteria.key()), criteria.term());
        }
        case GT: return builder.greaterThan(root.get(criteria.key()), criteria.term());
        case LT: return builder.lessThan(root.get(criteria.key()), criteria.term());
        case NE: return builder.notEqual(root.get(criteria.key()), criteria.term());
        default: {
            logger.error("Unhandled operator type: {}", criteria.operator());
            return builder.equal(root.get(criteria.key()), criteria.term());
        }
        }  // switch
    }

}
