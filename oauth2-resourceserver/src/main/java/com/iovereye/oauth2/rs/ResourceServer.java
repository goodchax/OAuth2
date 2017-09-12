package com.iovereye.oauth2.rs;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.message.types.ParameterStyle;
import com.iovereye.oauth2.common.utils.OAuthUtils;
import com.iovereye.oauth2.common.validators.OAuthValidator;
import com.iovereye.oauth2.rs.extractor.TokenExtractor;

public class ResourceServer {

	protected Map<ParameterStyle, Class<?>> extractors = new HashMap<ParameterStyle, Class<?>>();
	protected Map<ParameterStyle, Class<?>> validators = new HashMap<ParameterStyle, Class<?>>();
	
	@SuppressWarnings("rawtypes")
	public OAuthValidator instantiateValidator(ParameterStyle ps) throws OAuthSystemException {
		Class<?> clazz = validators.get(ps);
		if (clazz == null) {
			throw new OAuthSystemException("Cannot instantiate a message validator.");
		}
		return (OAuthValidator) OAuthUtils.instantiateClass(clazz);
	}
	
	public TokenExtractor instantiateExtractor(ParameterStyle ps) throws OAuthSystemException {
		Class<?> clazz = extractors.get(ps);
		if (clazz == null) {
			throw new OAuthSystemException("Cannot instantiate a token extractor");
		}
		return (TokenExtractor) OAuthUtils.instantiateClass(clazz);
	}
	
	public static String[] getQueryParameterValues(HttpServletRequest request, String name) {
		String query = request.getQueryString();
		if (query == null) {
			return null;
		}
		List<String> values = new ArrayList<String>();
		String[] params = query.split("&");
		for (String param : params) {
			try {
				param = URLDecoder.decode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			int index = param.indexOf("=");
			String key = param;
			String value = null;
			if (index != -1) {
				key = param.substring(0, index);
				value = param.substring(index +1);
			}
			if (key.equals(name)) {
				values.add(value);
			}
		}
		return values.toArray(new String[values.size()]);
	}
	
	public static String getQueryParameterValue(HttpServletRequest request, String name) {
        String[] values = getQueryParameterValues(request, name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }
	
}
