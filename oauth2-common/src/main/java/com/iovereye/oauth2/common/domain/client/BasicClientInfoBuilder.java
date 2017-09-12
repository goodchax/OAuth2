package com.iovereye.oauth2.common.domain.client;


public class BasicClientInfoBuilder {
	
	private BasicClientInfo info;

	private BasicClientInfoBuilder() {
		info = new BasicClientInfo();
	}

	public static BasicClientInfoBuilder clientInfo() {
		return new BasicClientInfoBuilder();
	}

	public BasicClientInfo build() {
		return info;
	}

	public BasicClientInfoBuilder setName(String value) {
		info.setName(value);
		return this;
	}

	public BasicClientInfoBuilder setClientId(String value) {
		info.setClientId(value);
		return this;
	}

	public BasicClientInfoBuilder setClientUrl(String value) {
		info.setClientUri(value);
		return this;
	}

	public BasicClientInfoBuilder setClientSecret(String value) {
		info.setClientSecret(value);
		return this;
	}

	public BasicClientInfoBuilder setIconUri(String value) {
		info.setIconUri(value);
		return this;
	}

	public BasicClientInfoBuilder setRedirectUri(String value) {
		info.setRedirectUri(value);
		return this;
	}

	public BasicClientInfoBuilder setDescription(String value) {
		info.setDescription(value);
		return this;
	}

	public BasicClientInfoBuilder setExpiresIn(Long value) {
		info.setExpiresIn(value);
		return this;
	}

	public BasicClientInfoBuilder setIssuedAt(Long value) {
		info.setIssuedAt(value);
		return this;
	}
}
