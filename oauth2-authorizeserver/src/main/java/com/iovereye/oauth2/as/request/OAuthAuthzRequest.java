package com.iovereye.oauth2.as.request;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.as.validators.CodeValidator;
import com.iovereye.oauth2.as.validators.TokenValidator;
import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.message.types.ResponseType;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.OAuthValidator;

public class OAuthAuthzRequest extends OAuthRequest {

	public OAuthAuthzRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }
	
	@Override
	protected OAuthValidator<HttpServletRequest> initValidator() throws OAuthProblemException, OAuthSystemException {
		validators.put(ResponseType.CODE.toString(), CodeValidator.class);
		validators.put(ResponseType.TOKEN.toString(), TokenValidator.class);
		final String requestTypeValue = getParam(OAuth.OAUTH_RESPONSE_TYPE);
		if (OAuthUtils.isEmpty(requestTypeValue)) {
			throw new OAuthProblemException(OAuthState.unsupported_response_type);
		}
		final Class<? extends OAuthValidator<HttpServletRequest>> clazz = validators.get(requestTypeValue);
		if (clazz == null) {
			throw new OAuthProblemException(OAuthState.unsupported_response_type);
		}
		
		return OAuthUtils.instantiateClass(clazz);
	}

	public String getState() {
        return getParam(OAuth.OAUTH_STATE);
    }

    public String getResponseType() {
        return getParam(OAuth.OAUTH_RESPONSE_TYPE);
    }
	
}
