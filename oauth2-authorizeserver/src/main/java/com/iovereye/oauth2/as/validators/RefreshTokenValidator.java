package com.iovereye.oauth2.as.validators;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.validators.AbstractValidator;

public class RefreshTokenValidator extends AbstractValidator<HttpServletRequest> {

	public RefreshTokenValidator() {
		requiredParams.add(OAuth.OAUTH_CLIENT_ID);
		requiredParams.add(OAuth.OAUTH_GRANT_TYPE);
		requiredParams.add(OAuth.OAUTH_TIMESTAMP);
		requiredParams.add(OAuth.OAUTH_REFRESH_TOKEN);
		enforceClientAuthentication = true;
	}
	

}
