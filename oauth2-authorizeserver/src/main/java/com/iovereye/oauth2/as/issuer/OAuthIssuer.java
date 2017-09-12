package com.iovereye.oauth2.as.issuer;

import com.iovereye.oauth2.common.exception.OAuthSystemException;


public interface OAuthIssuer {
	public String accessToken() throws OAuthSystemException;

    public String authorizationCode() throws OAuthSystemException;

    public String refreshToken() throws OAuthSystemException;
}
