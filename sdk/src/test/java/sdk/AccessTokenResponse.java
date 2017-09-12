package sdk;

import com.iovereye.sdk.IovereyeResponse;
import com.iovereye.sdk.internal.mapping.ApiField;

public class AccessTokenResponse extends IovereyeResponse {

	private static final long serialVersionUID = -3394840184200577272L;

	@ApiField("accessToken")
	private String accessToken;
	
	@ApiField("expiresIn")
	private Long expiresIn;
	
	@ApiField("refreshToken")
	private String refreshToken;
	
	@ApiField("scope")
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}	
	
}