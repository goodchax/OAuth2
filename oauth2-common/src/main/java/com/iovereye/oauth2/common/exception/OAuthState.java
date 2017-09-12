package com.iovereye.oauth2.common.exception;


public enum OAuthState {
	
	/**
	 * 重定向地址不匹配
	 */
	redirect_uri_mismatch(101, "redirect_uri_mismatch"),
	/**
	 * 请求不合法
	 */
	invalid_request(102, "invalid_request"),
	/**
	 * client_id或client_secret参数无效
	 */
	invalid_client(103, "invalid_client"),
	/**
	 * 提供的Access Grant是无效的、过期的或已撤销的
	 */
	invalid_grant(104, "invalid_grant"),
	/**
	 * 客户端没有权限
	 */
	unauthorized_client(105, "unauthorized_client"),
	/**
	 * token过期
	 */
	expired_token(106, "expired_token"),
	/**
	 * 不支持的 GrantType
	 */
	unsupported_grant_type(107, "unsupported_grant_type"),
	/**
	 * 不支持的 ResponseType
	 */
	unsupported_response_type(108, "unsupported_response_type"),
	/**
	 * 用户或授权服务器拒绝授予数据访问权限
	 */
	access_denied(109, "access_denied"),
	/**
	 * 服务暂时无法访问
	 */
	temporarily_unavailable(110, "temporarily_unavailable"),
	/**
	 * 应用权限不足
	 */
	appkey_permission_denied(111, "appkey_permission_denied"),
	
	/**
	 * sign不正确
	 */
	signature_invalid(112, "signture_invalid"),
	
	/**
	 * code过期
	 */
	expired_code(113,"expired_code"),
	/**
	 * expired_refreshToken过期
	 */
	expired_refreshToken(114,"expired_refreshToken"),
	
	/**
	 * Token获取失败
	 */
	keep_token(115,"keep_token"),
	/**
	 * code获取失败
	 */
	keep_code(116,"keep_code"),
	/**
	 * 缺少timestamp
	 */
	lack_timestamp(117,"lack_timestamp"),
	/**
	 * 时间戳超过
	 */
	exceed_timestamp(118,"exceed_timestamp"),
	
	/**
	 * 
	 */
	sign_method_invalid(119,"sign_method_invalid"),
	
	;
	
	
	private final int code;

	private final String error;
	
	private OAuthState(int code, String error) {
		this.code = code;
		this.error = error;
	}
	
	public int getCode() {
		return code;
	}

	public String getError() {
		return error;
	}
	
	public static OAuthState value(int code) {
		for (OAuthState e : values()) {
			if (e.code == code) {
				return e;
			}
		}
		return null;
	}
	
	public static OAuthState value(String error) {
		for (OAuthState e : values()) {
			if (e.error == error) {
				return e;
			}
		}
		return null;
	}
	
}
