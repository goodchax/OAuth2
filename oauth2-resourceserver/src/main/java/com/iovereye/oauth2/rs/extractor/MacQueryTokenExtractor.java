package com.iovereye.oauth2.rs.extractor;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.rs.ResourceServer;

public class MacQueryTokenExtractor implements TokenExtractor {

	@Override
	public String getAccessToken(HttpServletRequest request) {
		String token = ResourceServer.getQueryParameterValue(request, OAuth.OAUTH_ACCESS_TOKEN);
		if (token == null) {
			token = ResourceServer.getQueryParameterValue(request, OAuth.OAUTH_CLIENT_ID);
		}
		return token;
	}

	@Override
	public String getAccessToken(HttpServletRequest request, String tokenName) {
		return ResourceServer.getQueryParameterValue(request, tokenName);
	}

	
	
}