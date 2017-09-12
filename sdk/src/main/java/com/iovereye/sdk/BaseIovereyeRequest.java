package com.iovereye.sdk;

import java.util.Map;

import com.iovereye.sdk.internal.util.IovereyeHashMap;

public abstract class BaseIovereyeRequest<T extends IovereyeResponse> implements IovereyeRequest<T> {

	protected Map<String, String> headerMap; // HTTP请求头参数
	protected IovereyeHashMap otherTextlParams; // 自定义表单参数
	protected Long timestamp; // 请求时间戳

	public Map<String, String> getOtherTextParam() {
		if (this.otherTextlParams == null) {
			this.otherTextlParams = new IovereyeHashMap();
		}
		return this.otherTextlParams;
	}
	
	/**
	 * 添加URL自定义请求参数。
	 */
	public void putOtherTextParam(String key, String value) {
		getOtherTextParam().put(key, value);
	}

	public Map<String, String> getHeaderMap() {
		if (this.headerMap == null) {
			this.headerMap = new IovereyeHashMap();
		}
		return this.headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	/**
	 * 添加头部自定义请求参数。
	 */
	public void putHeaderParam(String key, String value) {
		getHeaderMap().put(key, value);
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
