package com.iovereye.oauth2.as.request;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.OAuthValidator;

public class AbstractOAuthTokenRequest extends OAuthRequest {

	protected AbstractOAuthTokenRequest(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
		super(request);
	}
	
	@Override
	protected OAuthValidator<HttpServletRequest> initValidator()
			throws OAuthProblemException, OAuthSystemException {
		final String requestTypeValue = getParam(OAuth.OAUTH_GRANT_TYPE);
		if (OAuthUtils.isEmpty(requestTypeValue)) {
			throw OAuthUtils.handleMissingParameters(OAuth.OAUTH_GRANT_TYPE);
		}
		final Class<? extends OAuthValidator<HttpServletRequest>> clazz = validators.get(requestTypeValue);
		if (clazz == null) {
			throw OAuthUtils.handleOAuthProblemException(OAuthState.invalid_grant);
		}
		return OAuthUtils.instantiateClass(clazz);
	}
	
	public String getUsername() {
		return getParam(OAuth.OAUTH_USERNAME);
	}
	
	public String getPassword() {
		return getParam(OAuth.OAUTH_PASSWORD);
	}
	
	public String getRefreshToken() {
		return getParam(OAuth.OAUTH_REFRESH_TOKEN);
	}
	
	public String getCode() {
		return getParam(OAuth.OAUTH_CODE);
	}
	
	public String getGrantType() {
		return getParam(OAuth.OAUTH_GRANT_TYPE);
	}
	

}
