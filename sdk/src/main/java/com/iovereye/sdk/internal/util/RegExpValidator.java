package com.iovereye.sdk.internal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证工具
 * @author Jiemo
 *
 */

public class RegExpValidator {

	/**
	 * email 格式是否正确
	 * 
	 * <ul>由字母、数字、下划线、中线组成</ul>
	 * <ul>长度不能超过32位</ul>
	 * 
	 * @param email 待验证的字符串
	 * @return　如果是符合邮箱格式的字符串,返回 <b>true</b>,否则为 <b>false</b>;
	 */
	public static boolean isEmail(String email) {
		if(email.length() > 32) 
			return false;
		
		String regex = "^[\\w-]{1,15}@(([a-zA-Z0-9-]){1,10}\\.)+[a-zA-Z]{1,5}$";
		return match(regex, email);
	}
	
	/**
	 * 昵称 格式是否正确
	 * 
	 * <ul>由汉字、字母、数字、下划线、中线组成</ul>
	 * <ul>长度不能超过20位</ul>
	 * 
	 * @param nickname
	 * @return
	 */
	public static boolean isNickName(String nickname) {
		if(nickname.length() > 20) 
			return false;
		
		String regex = "^[\u4e00-\u9fa5\\w-]{2,20}$";
		return match(regex, nickname);
	}
	
	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 * 
	 * @param obj	需要被检验的字符串
	 * @return Boolean 是数字返回 <b>true</b>,否则返回 <b>false</b>
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if(length < 1)
			return false;
		
		int i = 0;
		if(length > 1 && chars[0] == '-')
			i = 1;
		
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 正则表达式匹配方法
	 * 
	 * @param regex　正则表达式字符串
	 * @param str	等待匹配的字符串
	 * @return　如果str符合regex的正则格式,返回 <b>true</b>,否则返回 <b>false</b>;
	 */
	public static boolean match(String regex, String str) {

		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(str);	
		
		return matcher.matches();
		
	}
	
}
