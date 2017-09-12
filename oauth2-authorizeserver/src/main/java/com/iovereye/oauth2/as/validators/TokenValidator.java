package com.iovereye.oauth2.as.validators;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.AbstractValidator;

public class TokenValidator extends AbstractValidator<HttpServletRequest> {
	
	public TokenValidator() {
		requiredParams.add(OAuth.OAUTH_RESPONSE_TYPE);
		requiredParams.add(OAuth.OAUTH_CLIENT_ID);
		/*requiredParams.add(OAuth.OAUTH_REDIRECT_URI);*/
	}

	public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
		String method = request.getMethod();
		if (!OAuth.HttpMethod.GET.equals(method) && !OAuth.HttpMethod.POST.equals(method)) {
			throw OAuthUtils.handleOAuthProblemException(OAuthState.invalid_request);
		}
		
	}

	public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
		
	}

}
