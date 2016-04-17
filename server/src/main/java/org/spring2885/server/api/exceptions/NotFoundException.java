package org.spring2885.server.api.exceptions;

public class NotFoundException extends ApiException {
    private static final long serialVersionUID = 1L;

    public NotFoundException (String msg) {
		super(msg);
	}
}
