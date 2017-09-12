package com.iovereye.sdk;

import java.io.Serializable;
import java.util.Map;

import com.iovereye.sdk.internal.mapping.ApiField;

public abstract class IovereyeResponse implements Serializable {

	private static final long serialVersionUID = 7067148970531690343L;

	@ApiField(value="err_code")
	private String err_code;
	
	@ApiField(value="err_msg")
	private String err_msg;
	
	@ApiField(value="err_uri")
	private String err_uri;
	
	@ApiField(value="sub_code")
	private String sub_code;
	
	private String body;
	private Map<String, String> params;			

	

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getErr_uri() {
		return err_uri;
	}

	public void setErr_uri(String err_uri) {
		this.err_uri = err_uri;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isSuccess() {
		return this.err_msg == null;
	}
	
}
