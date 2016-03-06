package org.spring2885.server.db.service.search;

public final class SearchCriteria {
    private final String key;
    private final SearchOperator operator;
    private final String term;
    
    public SearchCriteria(String key, SearchOperator operator, String term) {
        this.key = key;
        this.operator = operator;
        this.term = term;
    }
    
    // Not using JavaBean patterns (getXxx) since this is not a JavaBean.
    public SearchOperator operator() { return operator; }
    public String key() { return key; }
    public String term() { return term; }
    
    @Override
    public String toString() {
        return new StringBuilder("{").append(key).append(' ').append(operator).append(' ').append(term).append("}").toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((term == null) ? 0 : term.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SearchCriteria other = (SearchCriteria) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (operator != other.operator)
            return false;
        if (term == null) {
            if (other.term != null)
                return false;
        } else if (!term.equals(other.term))
            return false;
        return true;
    }
}
