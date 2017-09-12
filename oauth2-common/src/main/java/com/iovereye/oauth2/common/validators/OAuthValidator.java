package com.iovereye.oauth2.common.validators;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.exception.OAuthProblemException;

public interface OAuthValidator<T extends HttpServletRequest> {

	public void validateMethod(T request) throws OAuthProblemException;

    public void validateContentType(T request) throws OAuthProblemException;

    public void validateRequiredParameters(T request) throws OAuthProblemException;

    public void validateOptionalParameters(T request) throws OAuthProblemException;

    public void validateNotAllowedParameters(T request) throws OAuthProblemException;

    public void validateClientAuthenticationCredentials(T request) throws OAuthProblemException;

    public void performAllValidations(T request) throws OAuthProblemException;
	
}
