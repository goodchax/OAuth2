package com.iovereye.oauth2.rs.validators;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.AbstractValidator;
import com.iovereye.oauth2.rs.ResourceServer;

public class MacQueryOAuthValidator extends AbstractValidator<HttpServletRequest> {

	public MacQueryOAuthValidator() {
		requiredParams.add(OAuth.OAUTH_CLIENT_ID);
		requiredParams.add(OAuth.OAUTH_TIMESTAMP);
		requiredParams.add(OAuth.OAUTH_SIGNATURE);
	}
	
	@Override
	public void validateContentType(HttpServletRequest request)
			throws OAuthProblemException {
	}
	
	@Override
	public void validateMethod(HttpServletRequest request)
			throws OAuthProblemException {
	}
	
	@Override
	public void validateRequiredParameters(HttpServletRequest request)
			throws OAuthProblemException {
		
		super.validateRequiredParameters(request);
		
		String[] tokens = ResourceServer.getQueryParameterValues(request, OAuth.OAUTH_ACCESS_TOKEN);
        if (OAuthUtils.hasEmptyValues(tokens)) {
            tokens = ResourceServer.getQueryParameterValues(request, OAuth.OAUTH_CLIENT_ID);
            if (OAuthUtils.hasEmptyValues(tokens)) {
                throw OAuthUtils.handleMissingParameters(OAuth.OAUTH_ACCESS_TOKEN);
            }
        }

        if (tokens != null && tokens.length > 1) {
            throw OAuthUtils.handleOAuthProblemException(OAuthState.invalid_request);
        }       

	}
	
}
