package com.iovereye.oauth2.common.signature;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.iovereye.oauth2.common.OAuth;
import com.iovereye.oauth2.common.OAuth.Parameter;
import com.iovereye.oauth2.common.exception.OAuthProblemException;
import com.iovereye.oauth2.common.exception.OAuthState;
import com.iovereye.oauth2.common.exception.OAuthSystemException;
import com.iovereye.oauth2.common.utils.OAuthUtils;

public abstract class OAuthSignatureMethod {

	/**
	 * Compute the signature for the given base string.
	 * 
	 * @throws OAuthProblemException
	 */
	protected abstract String getSignature(String baseString);

	/**
	 * Decide whether the signature is valid.
	 * 
	 * @throws OAuthProblemException
	 */
	protected abstract boolean isValid(String signature, String baseString);

	private String name;

	private String clientSecret;

	private String accessToken;

	public String getName() {
		return name;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void validate(HttpServletRequest request, String clientSecret)
			throws OAuthProblemException, URISyntaxException, IOException {
		String clientAuthHeader = request.getHeader(OAuth.HeaderType.AUTHORIZATION);
		String[] clientCreds = OAuthUtils.decodeClientAuthenticationHeader(clientAuthHeader);

		String signature;

		// Only fallback to params if the auth header is not correct. Don't
		// allow a mix of auth header vs params
		if (clientCreds == null || OAuthUtils.isEmpty(clientCreds[0]) || OAuthUtils.isEmpty(clientCreds[1])) {
			signature = request.getParameter(OAuth.OAUTH_SIGNATURE);
		} else {
			signature = clientCreds[1];
		}
		if (signature == null) {
			throw OAuthUtils.handleMissingParameters(OAuth.OAUTH_SIGNATURE);
		}
		setClientSecret(clientSecret);
		setAccessToken(request.getParameter(OAuth.OAUTH_ACCESS_TOKEN));
		String baseString = getBaseString(request);
		if (!isValid(signature, baseString)) {
			throw OAuthUtils.handleOAuthProblemException(OAuthState.signature_invalid);
		}
	}

	public static String getBaseString(HttpServletRequest request) throws URISyntaxException, IOException {
		List<Map.Entry<String, String>> parameters;
		String url = request.getRequestURL().toString();
		int q = url.indexOf('?');
		if (q < 0) {
			parameters = getParameters(request);
		} else {
			// Combine the URL query string with the other parameters:
			parameters = new ArrayList<Map.Entry<String, String>>();
			parameters.addAll(OAuthUtils.decodeForm(url.substring(q + 1)));
			parameters.addAll(getParameters(request));
			url = url.substring(0, q);
		}
		return normalizeParameters(parameters);
	}

	public static List<Map.Entry<String, String>> getParameters(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getParameterNames();
		List<Map.Entry<String, String>> parameters = new ArrayList<Map.Entry<String, String>>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (name == null)
				continue;
			String value = request.getParameter(name);
			parameters.add(new Parameter(name, value));
		}
		return parameters;
	}

	/**
	 * Subsequently, newMethod(name) will attempt to instantiate the given
	 * class, with no constructor parameters.
	 */
	public static void registerMethodClass(String name, Class<? extends OAuthSignatureMethod> clazz) {
		if (clazz == null)
			unregisterMethod(name);
		else
			NAME_TO_CLASS.put(name, clazz);
	}

	/**
	 * Subsequently, newMethod(name) will fail.
	 */
	public static void unregisterMethod(String name) {
		NAME_TO_CLASS.remove(name);
	}

	private static final Map<String, Class<? extends OAuthSignatureMethod>> NAME_TO_CLASS = new ConcurrentHashMap<String, Class<? extends OAuthSignatureMethod>>();
	static {
		registerMethodClass("HMAC", HMAC_SHA1.class);
		registerMethodClass("MD5", HMAC_MD5.class);
	}

	public static OAuthSignatureMethod newSigner(HttpServletRequest request) throws OAuthSystemException {
		OAuthSignatureMethod signer = null;
		String name = request.getParameter(OAuth.OAUTH_SIGNATURE_METHOD);
		if (OAuthUtils.isEmpty(name)) {
			name = "HMAC";
		}
		Class<? extends OAuthSignatureMethod> methodClass = NAME_TO_CLASS.get(name.toUpperCase());
		if (methodClass != null) {
			signer = (OAuthSignatureMethod) OAuthUtils.instantiateClass(methodClass);
		}
		return signer;
	}

	protected static String normalizeUrl(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String scheme = uri.getScheme().toLowerCase();
		String authority = uri.getAuthority().toLowerCase();
		boolean dropPort = (scheme.equals("http") && uri.getPort() == 80)
				|| (scheme.equals("https") && uri.getPort() == 443);
		if (dropPort) {
			// find the last : in the authority
			int index = authority.lastIndexOf(":");
			if (index >= 0) {
				authority = authority.substring(0, index);
			}
		}
		String path = uri.getRawPath();
		if (path == null || path.length() <= 0) {
			path = "/"; // conforms to RFC 2616 section 3.2.2
		}
		// we know that there is no query and no fragment here.
		return scheme + "://" + authority + path;
	}

	@SuppressWarnings("rawtypes")
	protected static String normalizeParameters(Collection<? extends Map.Entry> parameters)
			throws IOException {
		if (parameters == null) {
			return "";
		}
		List<ComparableParameter> p = new ArrayList<ComparableParameter>(parameters.size());
		for (Map.Entry parameter : parameters) {
			if (!OAuth.OAUTH_SIGNATURE.equals(parameter.getKey())) {
				p.add(new ComparableParameter(parameter));
			}
		}
		Collections.sort(p);
		return OAuthUtils.formEncode(getParameters(p));
	}

	/** Retrieve the original parameters from a sorted collection. */
	@SuppressWarnings("rawtypes")
	private static List<Map.Entry> getParameters(Collection<ComparableParameter> parameters) {
		if (parameters == null) {
			return null;
		}
		List<Map.Entry> list = new ArrayList<Map.Entry>(parameters.size());
		for (ComparableParameter parameter : parameters) {
			list.add(parameter.value);
		}
		return list;
	}

	private static class ComparableParameter implements Comparable<ComparableParameter> {

		@SuppressWarnings("rawtypes")
		ComparableParameter(Map.Entry value) {
			this.value = value;
			String n = toString(value.getKey());
			String v = toString(value.getValue());
			this.key = OAuthUtils.percentEncode(n) + ' ' + OAuthUtils.percentEncode(v);
			// ' ' is used because it comes before any character
			// that can appear in a percentEncoded string.
		}

		@SuppressWarnings("rawtypes")
		final Map.Entry value;

		private final String key;

		private static String toString(Object from) {
			return (from == null) ? null : from.toString();
		}

		public int compareTo(ComparableParameter that) {
			return this.key.compareTo(that.key);
		}

		@Override
		public String toString() {
			return key;
		}

	}

	private static final String BASE64_ENCODING = "ISO-8859-1";
	private static final Base64 BASE64 = new Base64();

	public static String base64Encode(byte[] b) {
		byte[] b2 = BASE64.encode(b);
		try {
			return new String(b2, BASE64_ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e + "");
		}
		return new String(b2);
	}

	/**
	 * Determine whether the given arrays contain the same sequence of bytes.
	 * The implementation discourages a
	 * <a href="http://codahale.com/a-lesson-in-timing-attacks/">timing
	 * attack</a>.
	 */

	public static byte[] decodeBase64(String s) {
		byte[] b;
		try {
			b = s.getBytes(BASE64_ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e + "");
			b = s.getBytes();
		}
		return BASE64.decode(b);
	}

	public static boolean equals(byte[] a, byte[] b) {
		if (a == null)
			return b == null;
		else if (b == null)
			return false;
		else if (b.length <= 0)
			return a.length <= 0;
		byte diff = (byte) ((a.length == b.length) ? 0 : 1);
		int j = 0;
		for (int i = 0; i < a.length; ++i) {
			diff |= a[i] ^ b[j];
			j = (j + 1) % b.length;
		}
		return diff == 0;
	}
	
	public static String byte2hex(byte[] bytes) {
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

}
