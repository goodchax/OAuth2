package com.iovereye.oauth2.common;

import java.util.Map;

import com.iovereye.oauth2.common.message.types.ParameterStyle;
import com.iovereye.oauth2.common.message.types.TokenType;
import com.iovereye.oauth2.common.utils.OAuthUtils;

public final class OAuth {

	public static final class HttpMethod {
		public static final String POST = "POST";
		public static final String GET = "GET";
		public static final String DELETE = "delete";
		public static final String PUT = "PUT";
	}
	
	public static final class HeaderType {
		public static final String CONTENT_TYPE = "Content-Type";
		public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
		public static final String AUTHORIZATION = "Authorization";
	}
	
	public static final class ContentType {
		public static final String URL_ENCODED = "application/x-www-form-urlencoded";
		public static final String JSON = "application/json";
	}
	
	public static final String OAUTH_RESPONSE_TYPE = "response_type";
	public static final String OAUTH_CLIENT_ID = "client_id";
	public static final String OAUTH_CLIENT_SECRET = "client_secret";
	public static final String OAUTH_REDIRECT_URI = "redirect_uri";
	public static final String OAUTH_SCOPE = "scope";	
	public static final String OAUTH_GRANT_TYPE = "grant_type";
	public static final String OAUTH_USERNAME = "username";
    public static final String OAUTH_PASSWORD = "password";
	
    public static final String OAUTH_SIGNATURE_METHOD = "sign_method";
    public static final String OAUTH_SIGNATURE = "sign";
    public static final String OAUTH_TIMESTAMP = "timestamp";
    public static final String OAUTH_STATE = "state";
    public static final String OAUTH_view = "view";
    
	public static final String OAUTH_HEADER_NAME = "Bearer";
	
	//Authorization response params
	public static final String OAUTH_CODE = "code";
	public static final String OAUTH_ACCESS_TOKEN = "access_token";
	public static final String OAUTH_EXPIRES_IN = "expires_in";
	public static final String OAUTH_REFRESH_TOKEN = "refresh_token";
	
	public static final String OAUTH_TOKEN_TYPE = "token_type";
	
	public static final String OAUTH_TOKEN = "oauth_token";
	
	public static final ParameterStyle DEFAULT_PARAMETER_STYLE = ParameterStyle.QUERY;
    public static final TokenType DEFAULT_TOKEN_TYPE = TokenType.MAC;
    
    public static final String OAUTH_VERSION_DIFFER = "oauth_signature_method";
	
    public static class Parameter implements Map.Entry<String, String>  {
    	 public Parameter(String key, String value) {
             this.key = key;
             this.value = value;
         }

         private final String key;

         private String value;

         public String getKey() {
             return key;
         }

         public String getValue() {
             return value;
         }

         public String setValue(String value) {
             try {
                 return this.value;
             } finally {
                 this.value = value;
             }
         }

         @Override
         public String toString() {
             return OAuthUtils.percentEncode(getKey()) + '=' + OAuthUtils.percentEncode(getValue());
         }

         @Override
         public int hashCode()
         {
             final int prime = 31;
             int result = 1;
             result = prime * result + ((key == null) ? 0 : key.hashCode());
             result = prime * result + ((value == null) ? 0 : value.hashCode());
             return result;
         }

         @Override
         public boolean equals(Object obj)
         {
             if (this == obj)
                 return true;
             if (obj == null)
                 return false;
             if (getClass() != obj.getClass())
                 return false;
             final Parameter that = (Parameter) obj;
             if (key == null) {
                 if (that.key != null)
                     return false;
             } else if (!key.equals(that.key))
                 return false;
             if (value == null) {
                 if (that.value != null)
                     return false;
             } else if (!value.equals(that.value))
                 return false;
             return true;
         }
    }
    
}
