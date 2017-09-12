package com.iovereye.oauth2.common.token;

public interface OAuthToken {

    public String getAccessToken();

    public Long getExpiresIn();

    public String getRefreshToken();
    
    public Long getRefExpiresIn();

    public String getScope();
}

