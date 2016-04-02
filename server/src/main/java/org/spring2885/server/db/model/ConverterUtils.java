package org.spring2885.server.db.model;

class ConverterUtils {
    static java.sql.Date asSqlDate(java.util.Date d) {
        if (d == null) {
            return null;
        }
        return new java.sql.Date(d.getTime());
    }
}
