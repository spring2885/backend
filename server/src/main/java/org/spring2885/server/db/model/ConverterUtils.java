package org.spring2885.server.db.model;

import java.sql.Timestamp;

class ConverterUtils {
    static java.sql.Date asSqlDate(java.util.Date d) {
        if (d == null) {
            return null;
        }
        return new java.sql.Date(d.getTime());
    }

    static Timestamp asTimestamp(java.util.Date d) {
        if (d == null) {
            return null;
        }
        return new Timestamp(d.getTime());
    }
}
