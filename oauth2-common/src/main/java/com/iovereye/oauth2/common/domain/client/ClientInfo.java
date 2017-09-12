package com.iovereye.oauth2.common.domain.client;

public interface ClientInfo {

	String getClientId();

    String getClientSecret();

    Long getIssuedAt();
    
    Long getExpiresIn();

    String getRedirectUri();

    String getClientUri();

    String getDescription();

    String getName();

    String getIconUri();
	
}
