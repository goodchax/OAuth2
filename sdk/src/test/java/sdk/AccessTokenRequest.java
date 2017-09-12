package sdk;

import java.util.Map;

import com.iovereye.sdk.ApiRuleException;
import com.iovereye.sdk.BaseIovereyeRequest;
import com.iovereye.sdk.Constants;
import com.iovereye.sdk.internal.util.IovereyeHashMap;

public class AccessTokenRequest extends BaseIovereyeRequest<AccessTokenResponse>  {

	private IovereyeHashMap udfParams; // add user-defined text parameters
	private IovereyeHashMap headerMap = new IovereyeHashMap();
	private Long timestamp;
	
	private String grant_type;
	private String code;
	private String redirect_uri;
	
	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApiMethod() {
		return Constants.METHOD_POST;
	}

	public String getApiUri() {
		return "oauth2.access_token";
	}

	public Map<String, String> getTextParams() {
		IovereyeHashMap txtParams = new IovereyeHashMap();
		txtParams.put("grant_type", grant_type);
		txtParams.put("code", code);
		txtParams.put("redirect_uri", redirect_uri);
		if (this.udfParams != null) {
			txtParams.putAll(udfParams);
		}
		return txtParams;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Class<AccessTokenResponse> getResponseClass() {
		return AccessTokenResponse.class;
	}

	public void check() throws ApiRuleException {
				
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void putOtherTextParam(String key, String value) {
		if(this.udfParams == null) {
			this.udfParams = new IovereyeHashMap();
		}
		this.udfParams.put(key, value);
	}


	
	
	
}
