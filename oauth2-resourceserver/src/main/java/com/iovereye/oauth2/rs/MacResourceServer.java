package com.iovereye.oauth2.rs;

import com.iovereye.oauth2.common.message.types.ParameterStyle;
import com.iovereye.oauth2.rs.extractor.MacQueryTokenExtractor;
import com.iovereye.oauth2.rs.validators.MacQueryOAuthValidator;


public class MacResourceServer extends ResourceServer {

	public MacResourceServer() {
		 extractors.put(ParameterStyle.QUERY, MacQueryTokenExtractor.class);
		 validators.put(ParameterStyle.QUERY, MacQueryOAuthValidator.class);
	}

}
