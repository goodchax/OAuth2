package com.iovereye.oauth2.common.exception;

public class ErrorResponse {

	private String errMsg;
	
	private int errCode;
	
	private String errUri;
	
	private String subCode;
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	
	
	public String getErrUri() {
		return errUri;
	}
	public void setErrUri(String errUri) {
		this.errUri = errUri;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	
}