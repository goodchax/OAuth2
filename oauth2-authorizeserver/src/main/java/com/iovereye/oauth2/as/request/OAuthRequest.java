package com.iovereye.oauth2.as.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;




import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.OAuthValidator;

public abstract class OAuthRequest {

	protected HttpServletRequest request;
	protected OAuthValidator<HttpServletRequest> validator;
	protected Map<String, Class<? extends OAuthValidator<HttpServletRequest>>> validators = new HashMap<String, Class<? extends OAuthValidator<HttpServletRequest>>>();

	public OAuthRequest(HttpServletRequest request)
			throws OAuthProblemException, OAuthSystemException {
		this.request = request;
		validate();
	}

	public OAuthRequest() {
	}

	protected void validate() throws OAuthProblemException, OAuthSystemException {
		validator = initValidator();
		validator.validateMethod(request);
		validator.validateContentType(request);
		validator.validateRequiredParameters(request);
		validator.validateClientAuthenticationCredentials(request);
	}

	protected abstract OAuthValidator<HttpServletRequest> initValidator()
			throws OAuthProblemException, OAuthSystemException;

	public String getParam(String name) {
		return request.getParameter(name);
	}

	public String getClientId() {
		String[] creds = OAuthUtils.decodeClientAuthenticationHeader(request.getHeader(OAuth.HeaderType.AUTHORIZATION));
		if (creds != null) {
			return creds[0];
		}
		return getParam(OAuth.OAUTH_CLIENT_ID);
	}

	public String getClientSecret() {
		String[] creds = OAuthUtils.decodeClientAuthenticationHeader(request.getHeader(OAuth.HeaderType.AUTHORIZATION));
		if (creds != null) {
			return creds[1];
		}
		return getParam(OAuth.OAUTH_CLIENT_SECRET);
	}
	
	public String getRedirectURI() {
		return getParam(OAuth.OAUTH_REDIRECT_URI);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isClientAuthHeaderUsed() {
		return OAuthUtils.decodeClientAuthenticationHeader(request.getHeader(OAuth.HeaderType.AUTHORIZATION)) != null;
	}

	public Set<String> getScopes() {
		String scopes = getParam(OAuth.OAUTH_SCOPE);
		return OAuthUtils.decodeScopes(scopes);
	}

}
