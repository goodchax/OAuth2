package com.iovereye.oauth2.as.request;

import javax.servlet.http.HttpServletRequest;





import com.iovereye.oauth2.as.validators.AuthorizationCodeValidator;
import com.iovereye.oauth2.as.validators.RefreshTokenValidator;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.message.types.GrantType;
import com.iovereye.oauth2.common.validators.OAuthValidator;

public class OAuthTokenRequest extends AbstractOAuthTokenRequest {

	public OAuthTokenRequest(HttpServletRequest request)
			throws OAuthProblemException, OAuthSystemException {
		super(request);
	}
	
	@Override
	protected OAuthValidator<HttpServletRequest> initValidator()
			throws OAuthProblemException, OAuthSystemException {
//		validators.put(GrantType.PASSWORD.toString(), PasswordValidator.class);
//      validators.put(GrantType.CLIENT_CREDENTIALS.toString(), ClientCredentialValidator.class);
        validators.put(GrantType.AUTHORIZATION_CODE.toString(), AuthorizationCodeValidator.class);
        validators.put(GrantType.REFRESH_TOKEN.toString(), RefreshTokenValidator.class);
        return super.initValidator();
	}

}
