package com.iovereye.oauth2.as.issuer;

import com.iovereye.oauth2.common.exception.OAuthSystemException;


public interface ValueGenerator {
	public String generateValue() throws OAuthSystemException;

    public String generateValue(String param) throws OAuthSystemException;
}
