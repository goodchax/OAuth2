package com.iovereye.oauth2.rs.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.message.types.ParameterStyle;
import com.iovereye.oauth2.common.message.types.TokenType;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.OAuthValidator;
import com.iovereye.oauth2.rs.MacResourceServer;
import com.iovereye.oauth2.rs.ResourceServer;
import com.iovereye.oauth2.rs.extractor.TokenExtractor;

public class OAuthAccessResourceRequest {

	private HttpServletRequest request;
	private ParameterStyle[] parameterStyles = new ParameterStyle[] { OAuth.DEFAULT_PARAMETER_STYLE };
	private TokenType[] tokenTypes = new TokenType[] { OAuth.DEFAULT_TOKEN_TYPE };
	private ParameterStyle usedParameterStyle;
	private ResourceServer usedResourceServer;

	protected static Map<TokenType, Class<?>> tokens = new HashMap<TokenType, Class<?>>();

	private TokenExtractor extractor;

	{
		tokens.put(TokenType.MAC, MacResourceServer.class);
	}

	public OAuthAccessResourceRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
		this(request, new TokenType[] { OAuth.DEFAULT_TOKEN_TYPE },
				new ParameterStyle[] { OAuth.DEFAULT_PARAMETER_STYLE });
	}

	public OAuthAccessResourceRequest(HttpServletRequest request,
			ParameterStyle... paramterStyles) throws OAuthSystemException, OAuthProblemException {
		this(request, new TokenType[] { OAuth.DEFAULT_TOKEN_TYPE },
				paramterStyles);
	}

	public OAuthAccessResourceRequest(HttpServletRequest request,
			TokenType... tokenTypes) throws OAuthSystemException, OAuthProblemException {
		this(request, tokenTypes,
				new ParameterStyle[] { OAuth.DEFAULT_PARAMETER_STYLE });
	}

	public OAuthAccessResourceRequest(HttpServletRequest request,
			TokenType[] tokenTypes, ParameterStyle[] parameterStyles) throws OAuthSystemException, OAuthProblemException {
		this.request = request;
		this.tokenTypes = tokenTypes;
		this.parameterStyles = parameterStyles;
		this.validate();
	}

	public String getAccessToken() {
		return extractor.getAccessToken(request);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void validate() throws OAuthSystemException, OAuthProblemException {
		int foundValidStyles = 0;
        OAuthProblemException ex = null;
		for (TokenType tokenType : tokenTypes) {
			ResourceServer resourceServer = instantiateResourceServer(tokenType);
			for (ParameterStyle style : parameterStyles) {
				try {
					OAuthValidator validator = resourceServer.instantiateValidator(style);
					validator.validateContentType(request);
					validator.validateMethod(request);
					validator.validateRequiredParameters(request);
					
					usedParameterStyle = style;
					usedResourceServer = resourceServer;
					foundValidStyles ++;					
				} catch (OAuthProblemException e) {
					//request lacks any authentication information?
        			ex = e;
				}		
			}
		}
		
		if (foundValidStyles > 1) {
			throw OAuthUtils.handleOAuthProblemException(OAuthState.temporarily_unavailable);
		}
		
		if (ex != null) {
			throw ex;
		}
		
		if (foundValidStyles == 0) {
			throw OAuthUtils.handleOAuthProblemException(OAuthState.invalid_request);
		}
		
		extractor = usedResourceServer.instantiateExtractor(usedParameterStyle);
	}

	private ResourceServer instantiateResourceServer(TokenType tokenType)
			throws OAuthSystemException {
		Class<?> clazz = tokens.get(tokenType);
		if (clazz == null) {
			throw new OAuthSystemException(
					"Cannot instantiate a resource server.");
		}
		return (ResourceServer) OAuthUtils.instantiateClass(clazz);
	}

}
