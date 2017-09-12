package com.iovereye.oauth2.common.exception;

import java.util.HashMap;
import java.util.Map;

public class OAuthProblemException extends Exception {

	private static final long serialVersionUID = -1525383059522630773L;

	private int errCode;
	private String errMsg;
	private String subCode;

	private Map<String, String> args;

	/**
	 * 构造器
	 */
	public OAuthProblemException() {
	}

	public OAuthProblemException(OAuthState state) {
		this(state, null);
	}

	public OAuthProblemException(OAuthState state, String subCode) {
		this.errCode = state.getCode();
		this.errMsg = state.getError();
		this.subCode = subCode;
	}

	/**
	 * 设置子级错误码
	 * 
	 * @param sub_code
	 * @return
	 */
	public OAuthProblemException subCode(String subCode) {
		this.subCode = subCode;
		return this;
	}

	/**
	 * 给子级添加参数
	 * 
	 * @see ErrorSubCode.ArgKey
	 * @param k
	 *            如：***、###参考ErrorSubCode.ArgKey
	 * @param v
	 * @return
	 */
	public OAuthProblemException addArg(String k, String v) {
		if (this.args == null) {
			this.args = new HashMap<String, String>();
		}
		this.args.put(k, v);
		return this;
	}

	/**
	 * 添加***变量
	 * 
	 * @param arg
	 * @return
	 */
	public OAuthProblemException addAsteriskArg(String arg) {
		return addArg(ErrorSubCode.ArgKey.ASTERISK, arg);
	}

	/**
	 * 添加###变量
	 * 
	 * @param arg
	 * @return
	 */
	public OAuthProblemException addPoundSign(String arg) {
		return addArg(ErrorSubCode.ArgKey.POUND_SIGN, arg);
	}

	/**
	 * 获取错误码
	 * 
	 * @return
	 */
	public int getErrCode() {
		return errCode;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 获取子级错误
	 * 
	 * @return
	 */
	public String getSubCode() {
		return ErrorSubCode.replaceArgs(this.subCode, this.getArgs());
	}

	/**
	 * 获取子级错误参数
	 */
	public Map<String, String> getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		return this.getSubCode();
	}

}
