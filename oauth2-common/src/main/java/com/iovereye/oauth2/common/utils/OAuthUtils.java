package com.iovereye.oauth2.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.OAuth.Parameter;
import com.iovereye.oauth2.common.exception.ErrorSubCode;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthSystemException;

public final class OAuthUtils {

	public static final String ENCODING = "UTF-8";

	public static final String AUTH_SCHEME = OAuth.OAUTH_HEADER_NAME;

	public static final String MULTIPART = "multipart/";

	public static boolean isEmpty(String value) {
		return value == null || "".equals(value);
	}

	public static boolean hasEmptyValues(String[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		for (String s : array) {
			if (isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmpty(Set<String> missingParams) {
		if (missingParams == null || missingParams.size() == 0) {
			return true;
		}
		return false;
	}

	public static <T> T instantiateClass(Class<T> clazz) throws OAuthSystemException {
		return instantiateClassWithParameters(clazz, null, null);
	}

	public static <T> T instantiateClassWithParameters(Class<T> clazz, Class<?>[] paramsTypes, Object[] paramValues)
			throws OAuthSystemException {

		try {
			if (paramsTypes != null && paramValues != null) {
				if (!(paramsTypes.length == paramValues.length)) {
					throw new IllegalArgumentException("Number of types and values must be equal");
				}

				if (paramsTypes.length == 0 && paramValues.length == 0) {
					return clazz.newInstance();
				}
				Constructor<T> clazzConstructor = clazz.getConstructor(paramsTypes);
				return clazzConstructor.newInstance(paramValues);
			}
			return clazz.newInstance();

		} catch (NoSuchMethodException e) {
			throw new OAuthSystemException(e);
		} catch (InstantiationException e) {
			throw new OAuthSystemException(e);
		} catch (IllegalAccessException e) {
			throw new OAuthSystemException(e);
		} catch (InvocationTargetException e) {
			throw new OAuthSystemException(e);
		}

	}

	/**
	 * Decodes the Basic Authentication header into a username and password
	 * 
	 * @param authenticationHeader
	 *            {@link String} containing the encoded header value. e.g.
	 *            "Basic dXNlcm5hbWU6cGFzc3dvcmQ="
	 * @return a {@link String[]} if the header could be decoded into a non null
	 *         username and password or null.
	 */
	public static String[] decodeClientAuthenticationHeader(String authenticationHeader) {
		if (authenticationHeader == null || "".equals(authenticationHeader)) {
			return null;
		}
		String[] tokens = authenticationHeader.split(" ");
		if (tokens == null) {
			return null;
		}
		if (tokens[0] != null && !"".equals(tokens[0])) {
			String authType = tokens[0];
			if (!authType.equalsIgnoreCase("basic")) {
				return null;
			}
		}
		if (tokens[1] != null && !"".equals(tokens[1])) {
			String encodedCreds = tokens[1];
			String decodedCreds = new String(Base64.decodeBase64(encodedCreds));
			if (decodedCreds.contains(":") && decodedCreds.split(":").length == 2) {
				String[] creds = decodedCreds.split(":");
				if (!OAuthUtils.isEmpty(creds[0]) && !OAuthUtils.isEmpty(creds[1])) {
					return decodedCreds.split(":");
				}
			}
		}
		return null;
	}

	/**
	 * Construct a WWW-Authenticate header
	 */
	public static String encodeOAuthHeader(Map<String, Object> entries) {
		StringBuffer sb = new StringBuffer();
		sb.append(OAuth.OAUTH_HEADER_NAME).append(" ");
		for (Map.Entry<String, Object> entry : entries.entrySet()) {
			String value = entry.getValue() == null ? null : String.valueOf(entry.getValue());
			if (!OAuthUtils.isEmpty(entry.getKey()) && !OAuthUtils.isEmpty(value)) {
				sb.append(entry.getKey());
				sb.append("=\"");
				sb.append(value);
				sb.append("\",");
			}
		}

		return sb.substring(0, sb.length() - 1);
	}

	public static Set<String> decodeScopes(String s) {
		Set<String> scopes = new HashSet<String>();
		if (!OAuthUtils.isEmpty(s)) {
			StringTokenizer tokenizer = new StringTokenizer(s, " ");

			while (tokenizer.hasMoreElements()) {
				scopes.add(tokenizer.nextToken());
			}
		}
		return scopes;

	}

	public static String encodeScopes(Set<String> s) {
		StringBuffer scopes = new StringBuffer();
		for (String scope : s) {
			scopes.append(scope).append(" ");
		}
		return scopes.toString().trim();

	}

	public static boolean isMultipart(HttpServletRequest request) {

		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase().startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}

	public static boolean hasContentType(String requestContentType, String requiredContentType) {
		if (OAuthUtils.isEmpty(requiredContentType) || OAuthUtils.isEmpty(requestContentType)) {
			return false;
		}
		StringTokenizer tokenizer = new StringTokenizer(requestContentType, ";");
		while (tokenizer.hasMoreTokens()) {
			if (requiredContentType.equals(tokenizer.nextToken())) {
				return true;
			}
		}

		return false;
	}

	public static OAuthProblemException handleOAuthProblemException(OAuthState err) {
		return new OAuthProblemException(err);
	}

	public static OAuthProblemException handleMissingParameters(String missingParams) {
		StringBuffer sb = new StringBuffer();
		if (!OAuthUtils.isEmpty(missingParams)) {
			sb.append(missingParams);
		}
		return new OAuthProblemException(OAuthState.invalid_request, ErrorSubCode.ISV.MISSING_PARAMETER)
				.addArg(ErrorSubCode.ArgKey.ASTERISK, sb.toString().trim());
	}

	public static OAuthProblemException handleMissingParameters(Set<String> missingParams) {
		StringBuffer sb = new StringBuffer();
		if (!OAuthUtils.isEmpty(missingParams)) {
			for (String missingParam : missingParams) {
				sb.append(missingParam).append(" ");
			}
		}
		return new OAuthProblemException(OAuthState.invalid_request, ErrorSubCode.ISV.MISSING_PARAMETER)
				.addArg(ErrorSubCode.ArgKey.ASTERISK, sb.toString().trim());
	}

	public static OAuthProblemException handleNotAllowedParametersOAuthException(List<String> notAllowedParams) {
		StringBuffer sb = new StringBuffer();
		if (notAllowedParams != null) {
			for (String notAllowed : notAllowedParams) {
				sb.append(notAllowed).append(" ");
			}
		}
		return new OAuthProblemException(OAuthState.invalid_request, ErrorSubCode.ISV.MISSING_PARAMETER)
				.addArg(ErrorSubCode.ArgKey.ASTERISK, sb.toString().trim());
	}

	public static String percentEncode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s, ENCODING)
					// OAuth encodes some characters differently:
					.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
			// This could be done faster with more hand-crafted code.
		} catch (UnsupportedEncodingException wow) {
			throw new RuntimeException(wow.getMessage(), wow);
		}
	}

	public static String decodePercent(String s) {
		try {
			return URLDecoder.decode(s, ENCODING);
			// This implements http://oauth.pbwiki.com/FlexibleDecoding
		} catch (java.io.UnsupportedEncodingException wow) {
			throw new RuntimeException(wow.getMessage(), wow);
		}
	}

	/**
	 * Construct a form-urlencoded document containing the given sequence of
	 * name/value pairs. Use OAuth percent encoding (not exactly the encoding
	 * mandated by HTTP).
	 */
	@SuppressWarnings("rawtypes")
	public static String formEncode(Iterable<? extends Map.Entry> parameters) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		formEncode(parameters, b);
		return decodeCharacters(b.toByteArray());
	}

	/**
	 * Write a form-urlencoded document into the given stream, containing the
	 * given sequence of name/value pairs.
	 */
	@SuppressWarnings("rawtypes")
	public static void formEncode(Iterable<? extends Map.Entry> parameters, OutputStream into) throws IOException {
		if (parameters != null) {
			for (Map.Entry parameter : parameters) {
				into.write(encodeCharacters(percentEncode(toString(parameter.getKey()))));
				into.write(encodeCharacters(percentEncode(toString(parameter.getValue()))));
			}
		}
	}

	/** Parse a form-urlencoded document. */
	public static List<Parameter> decodeForm(String form) {
		List<Parameter> list = new ArrayList<Parameter>();
		if (!isEmpty(form)) {
			for (String nvp : form.split("\\&")) {
				int equals = nvp.indexOf('=');
				String name;
				String value;
				if (equals < 0) {
					name = decodePercent(nvp);
					value = null;
				} else {
					name = decodePercent(nvp.substring(0, equals));
					value = decodePercent(nvp.substring(equals + 1));
				}
				list.add(new Parameter(name, value));
			}
		}
		return list;
	}

	private static String characterEncoding = ENCODING;

	public static byte[] encodeCharacters(String from) {
		if (characterEncoding != null) {
			try {
				return from.getBytes(characterEncoding);
			} catch (UnsupportedEncodingException e) {
				System.err.println(e + "");
			}
		}
		return from.getBytes();
	}

	public static String decodeCharacters(byte[] from) {
		if (characterEncoding != null) {
			try {
				return new String(from, characterEncoding);
			} catch (UnsupportedEncodingException e) {
				System.err.println(e + "");
			}
		}
		return new String(from);
	}

	private static final String toString(Object from) {
		return (from == null) ? null : from.toString();
	}

}
