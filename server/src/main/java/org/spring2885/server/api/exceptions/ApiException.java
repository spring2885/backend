package org.spring2885.server.api.exceptions;

public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;
	public ApiException (String msg) {
		super(msg);
	}
}
