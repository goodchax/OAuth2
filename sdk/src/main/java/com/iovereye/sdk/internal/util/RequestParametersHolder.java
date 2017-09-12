package com.iovereye.sdk.internal.util;

import java.util.HashMap;
import java.util.Map;


public class RequestParametersHolder {

	private IovereyeHashMap protocalMustParams;
	private IovereyeHashMap protocalOptParams;
	private IovereyeHashMap textParams;

	public IovereyeHashMap getProtocalMustParams() {
		return protocalMustParams;
	}

	public void setProtocalMustParams(IovereyeHashMap protocalMustParams) {
		this.protocalMustParams = protocalMustParams;
	}

	public IovereyeHashMap getProtocalOptParams() {
		return protocalOptParams;
	}

	public void setProtocalOptParams(IovereyeHashMap protocalOptParams) {
		this.protocalOptParams = protocalOptParams;
	}

	public IovereyeHashMap getTextParams() {
		return textParams;
	}

	public void setTextParams(IovereyeHashMap textParams) {
		this.textParams = textParams;
	}

	public Map<String, String> getProtocalParams() {
		Map<String, String> params = new HashMap<String, String>();
		if (protocalMustParams != null && !protocalMustParams.isEmpty()) {
			params.putAll(protocalMustParams);
		}
		if (protocalOptParams != null && !protocalOptParams.isEmpty()) {
			params.putAll(protocalOptParams);
		}
		return params;
	}
	
	public Map<String, String> getAllParams() {
		Map<String, String> params = new HashMap<String, String>();
		if (protocalMustParams != null && !protocalMustParams.isEmpty()) {
			params.putAll(protocalMustParams);
		}
		if (protocalOptParams != null && !protocalOptParams.isEmpty()) {
			params.putAll(protocalOptParams);
		}
		if (textParams != null && !textParams.isEmpty()) {
			params.putAll(textParams);
		}
		return params;
	}

}