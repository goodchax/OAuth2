package com.iovereye.oauth2.common.exception;

public class OAuthSystemException extends Exception {

	private static final long serialVersionUID = -1608630254437521646L;

	public OAuthSystemException() {
        super();
    }

    public OAuthSystemException(String s) {
        super(s);
    }

    public OAuthSystemException(Throwable throwable) {
        super(throwable);
    }

    public OAuthSystemException(String s, Throwable throwable) {
        super(s, throwable);
    }
}