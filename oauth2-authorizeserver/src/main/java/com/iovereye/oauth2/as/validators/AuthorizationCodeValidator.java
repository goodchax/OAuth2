package com.iovereye.oauth2.as.validators;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.validators.AbstractValidator;

public class AuthorizationCodeValidator extends AbstractValidator<HttpServletRequest> {

	public AuthorizationCodeValidator() {
		requiredParams.add(OAuth.OAUTH_GRANT_TYPE);
		requiredParams.add(OAuth.OAUTH_CODE);
		requiredParams.add(OAuth.OAUTH_REDIRECT_URI);
		requiredParams.add(OAuth.OAUTH_TIMESTAMP);
		//requiredParams.add(OAuth.OAUTH_REDIRECT_URI);
		
		enforceClientAuthentication = true;
	}
	
	
	
}
