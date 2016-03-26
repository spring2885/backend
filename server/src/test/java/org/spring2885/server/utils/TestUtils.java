package org.spring2885.server.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilities to use in the Spring2885 Unit Tests.
 */
public class TestUtils {
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException  {
        return new ObjectMapper().writeValueAsBytes(object);
    }    

}
