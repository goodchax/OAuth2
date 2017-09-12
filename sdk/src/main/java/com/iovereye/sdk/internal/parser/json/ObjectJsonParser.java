package com.iovereye.sdk.internal.parser.json;

import com.iovereye.sdk.ApiException;
import com.iovereye.sdk.IovereyeParser;
import com.iovereye.sdk.IovereyeResponse;
import com.iovereye.sdk.internal.mapping.Converter;

public class ObjectJsonParser<T extends IovereyeResponse> implements IovereyeParser<T> {

	private Class<T> clazz;
	
	public ObjectJsonParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T parse(String rsp) throws ApiException {
		Converter converter;
		converter = new JsonConverter();
		
		return converter.toResponse(rsp, clazz);
	}

	public Class<T> getResponseClass() {
		return clazz;
	}

	
	
}
