package com.iovereye.oauth2.common.domain.credentials;

import java.io.Serializable;

public interface Credentials extends Serializable {
	
	String getClientId();

    String getClientSecret();
    
    Long getIssuedAt();

    Long getExpiresIn();
	
}
