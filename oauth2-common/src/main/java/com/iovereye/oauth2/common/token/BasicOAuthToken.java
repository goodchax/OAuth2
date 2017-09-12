package com.iovereye.oauth2.common.token;

public class BasicOAuthToken implements OAuthToken {

	protected String accessToken;
    protected Long expiresIn;
    protected String refreshToken;
    protected Long refExpiresIn;
    protected String scope;

    public BasicOAuthToken() {
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String refreshToken,Long refExpiresIn, String scope) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refExpiresIn = refExpiresIn;
        this.scope = scope;
    }

    public BasicOAuthToken(String accessToken) {
        this(accessToken, null, null,null, null);
    }

    public BasicOAuthToken(String accessToken, Long expiresIn) {
        this(accessToken, expiresIn, null,null, null);
    }
    
    public BasicOAuthToken(String accessToken, Long expiresIn,String refreshToken,String scope) {
    	  this.accessToken = accessToken;
          this.expiresIn = expiresIn;
          this.refreshToken = refreshToken;
          this.scope = scope;
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String scope) {
        this(accessToken, expiresIn, null,null, scope);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }

	@Override
	public Long getRefExpiresIn() {
		// TODO Auto-generated method stub
		return refExpiresIn;
	}

}
