package com.iovereye.sdk.internal.util.json;

public class JSONValidatingReader extends JSONReader {

	public JSONValidatingReader() {
		
	}
	
	public Object read(String string) {
		if (new JSONValidator().validate(string)) {
			return super.read(string);
		}
		return null;
	}
	
}
