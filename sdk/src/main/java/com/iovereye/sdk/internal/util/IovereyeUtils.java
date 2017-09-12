package com.iovereye.sdk.internal.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.iovereye.sdk.ApiException;
import com.iovereye.sdk.Constants;

public class IovereyeUtils {

	public static String urlTopRequest(String url, String method, String format) throws ApiException {
		if(!StringUtils.areNotEmpty(url, method)) {
			throw new ApiException("url参数不匹配。");
		}
		String u = ("/".equals(url.substring(url.length()-1)) ? url : url+"/");
		u += method.replace('.', '/');
		if (format != null) {
			u += (format.substring(0,1)=="." ? format : "."+format);
		}
		return u;
	}
	
	public static String signTopRequest(RequestParametersHolder requestHolder, String secret, String signMethod) throws IOException {
		return signTopRequest(requestHolder.getAllParams(), secret, signMethod);
	}
	
	public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
	    // 第一步：检查参数是否已经排序
	    String[] keys = params.keySet().toArray(new String[0]);
	    Arrays.sort(keys);
	 
	    // 第二步：把所有参数名和参数值串在一起
	    StringBuilder query = new StringBuilder();
	    if (Constants.SIGN_METHOD_MD5.equals(signMethod)) {
	        query.append(secret);
	    }
	    for (String key : keys) {
	        String value = params.get(key);
	        if (StringUtils.areNotEmpty(key, value)) {
	            query.append(key).append(value);
	        }
	    }
	 
	    // 第三步：使用MD5/HMAC加密
	    byte[] bytes;
	    if (Constants.SIGN_METHOD_HMAC.equals(signMethod)) {
	        bytes = encryptHMAC(query.toString(), secret);
	    } else {
	        query.append(secret);
	        bytes = encryptMD5(query.toString());
	    }
	 
	    // 第四步：把二进制转化为大写的十六进制
	    return byte2hex(bytes);
	}
	
	public static byte[] encryptMD5(String data) throws IOException {
	    return encryptMD5(data.getBytes(Constants.CHARSET_UTF8));
	}
	
	public static byte[] encryptMD5(byte[] bytes) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);  
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  
	      
	    return null;
	}
	

	private static byte[] encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), "HmacSHA1");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException e) {
			String msg = getStringFromException(e);
			throw new IOException(msg);
		}
		return bytes;
	}

	private static String getStringFromException(Throwable e) {
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		e.printStackTrace(ps);
		try {
			result = bos.toString(Constants.CHARSET_UTF8);
		} catch (IOException ioe) {
		}
		return result;
	}
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}
	
	/**
	 * 获取文件的真实后缀名。目前只支持JPG, GIF, PNG, BMP四种图片文件。
	 * 
	 * @param bytes 文件字节流
	 * @return JPG, GIF, PNG or null
	 */
	public static String getFileSuffix(byte[] bytes) {
		if (bytes == null || bytes.length < 10) {
			return null;
		}

		if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F') {
			return "GIF";
		} else if (bytes[1] == 'P' && bytes[2] == 'N' && bytes[3] == 'G') {
			return "PNG";
		} else if (bytes[6] == 'J' && bytes[7] == 'F' && bytes[8] == 'I' && bytes[9] == 'F') {
			return "JPG";
		} else if (bytes[0] == 'B' && bytes[1] == 'M') {
			return "BMP";
		} else {
			return null;
		}
	}

	/**
	 * 获取文件的真实媒体类型。目前只支持JPG, GIF, PNG, BMP四种图片文件。
	 * 
	 * @param bytes 文件字节流
	 * @return 媒体类型(MEME-TYPE)
	 */
	public static String getMimeType(byte[] bytes) {
		String suffix = getFileSuffix(bytes);
		String mimeType;

		if ("JPG".equals(suffix)) {
			mimeType = "image/jpeg";
		} else if ("GIF".equals(suffix)) {
			mimeType = "image/gif";
		} else if ("PNG".equals(suffix)) {
			mimeType = "image/png";
		} else if ("BMP".equals(suffix)) {
			mimeType = "image/bmp";
		}else {
			mimeType = "application/octet-stream";
		}

		return mimeType;
	}

	/**
	 * 清除字典中值为空的项。
	 * 
	 * @param <V> 泛型
	 * @param map 待清除的字典
	 * @return 清除后的字典
	 */
	public static <V> Map<String, V> cleanupMap(Map<String, V> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, V> result = new HashMap<String, V>(map.size());
		Set<Entry<String, V>> entries = map.entrySet();

		for (Entry<String, V> entry : entries) {
			if (entry.getValue() != null) {
				result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}
	
}
