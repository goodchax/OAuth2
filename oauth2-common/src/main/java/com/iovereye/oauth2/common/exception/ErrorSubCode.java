package com.iovereye.oauth2.common.exception;

import java.util.Iterator;
import java.util.Map;

public class ErrorSubCode {

	public static class ArgKey {
		/**
		 * [计]磅字符(#)、井字号（pound sign）
		 */
		public static final String POUND_SIGN = "###";

		/**
		 * 星号(asterisk)
		 */
		public static final String ASTERISK = "\\*\\*\\*";
	}

	/**
	 * 把子级错误中的参数替换成参数值。 当sub_code 和 args都不为空时，才执行替换
	 * 
	 * @param sub_code 子级错误码
	 * @param args 参数集
	 * @return 替换后的sub_code
	 */
	public static String replaceArgs(String sub_code, Map<String, String> args) {
		if (sub_code != null && args != null) {
			Iterator<String> it = args.keySet().iterator();
			while (it.hasNext()) {
				String k = it.next();
				String v = args.get(k);
				sub_code = sub_code.replaceAll(k, v);
			}
		}
		return sub_code;
	}

	/**
	 * 平台子级错误
	 */
	public static class ISP {

	}

	/**
	 * 业务子级错误
	 * 
	 * @author Administrator
	 * 
	 */
	public static class ISV {

		/**
		 * isv.###-not-exist:*** -> 根据***查询不到###
		 */
		public static final String NOT_EXIST = "isv.###-not-exist:***";

		/**
		 * isv.###-not-exist:*** -> 根据***查询,###已经存在
		 */
		public static final String EXIST = "isv.###-exist:***";

		/**
		 * isv.missing-parameter:*** -> 缺少必要的参数***
		 */
		public static final String MISSING_PARAMETER = "isv.missing-parameter:***";

		/**
		 * isv.invalid-paramete:*** -> 参数***无效，格式不对、非法值、越界等
		 */
		public static final String INVALID_PARAMETER = "isv.invalid-parameter:***";

		/**
		 * 权限不够、非法访问
		 */
		public static final String INVALID_PERMISSION = "isv.invalid-permission";

		/**
		 * isv.parameters-mismatch:***-and-### -> 传入的参数***和###不匹配，两者有一定的对应关系
		 */
		public static final String PARAMETERS_MISMATCH = "isv.parameters-mismatch:***-and-###";

		/**
		 * isv.parameter-engaged:*** -> 参数***被占用
		 */
		// public static final String PARAMETER_ENGAGED = "isv.parameter-engaged";

		public static final String NOT_ALLOWED_PARAMETER = "isv.not-allowed-parameter:***";

	}

}
