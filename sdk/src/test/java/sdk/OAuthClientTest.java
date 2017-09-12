package sdk;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.iovereye.sdk.ApiException;
import com.iovereye.sdk.IovereyeClient;
import com.iovereye.sdk.OAuthIovereyeClient;

public class OAuthClientTest {

	@Test
	public void test() {
		
		AccessTokenRequest request = new AccessTokenRequest();
		request.setCode("75e386c87e0bcaa62a78862550803bb1");
		request.setGrant_type("authorization_code");
		request.setRedirect_uri("abc");
		request.setTimestamp(System.currentTimeMillis());
		
		try {
			IovereyeClient client = new OAuthIovereyeClient("http://localhost:8080/iovereye-oauth2", "123", "123");
			AccessTokenResponse response = client.execute(request, "test");
			System.out.println(response.toString());
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}

}
