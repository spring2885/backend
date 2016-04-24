package org.spring2885.server.db.model;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class ConverterUtils {
    public static java.sql.Date asSqlDate(java.util.Date d) {
        if (d == null) {
            return null;
        }
        return new java.sql.Date(d.getTime());
    }

    public static Timestamp asTimestamp(java.util.Date d) {
        if (d == null) {
            return null;
        }
        return new Timestamp(d.getTime());
    }

    public static java.sql.Date asSqlDate(DateTime d) {
        if (d == null) {
            return null;
        }
        return new java.sql.Date(d.getMillis());
    }

    public static Timestamp asTimestamp(DateTime d) {
        if (d == null) {
            return null;
        }
        return new Timestamp(d.getMillis());
    }

    public static DateTime asModelDate(java.sql.Timestamp d) {
        if (d == null) {
            return null;
        }
        return new DateTime(d.getTime());
    }
    
    public static DateTime asModelDate(java.sql.Date d) {
        if (d == null) {
            return null;
        }
        return new DateTime(d.getTime());
    }
}
