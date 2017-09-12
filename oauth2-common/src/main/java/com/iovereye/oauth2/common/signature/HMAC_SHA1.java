package com.iovereye.oauth2.common.signature;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.iovereye.oauth2.common.utils.OAuthUtils;

public class HMAC_SHA1 extends OAuthSignatureMethod {

	@Override
    protected String getSignature(String baseString) {
        try {
            String signature = computeSignature(baseString).toString();
            return signature;
        } catch (GeneralSecurityException e) {
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected boolean isValid(String signature, String baseString) {
			try {
				String str = byte2hex(computeSignature(baseString));
				byte[] expected = decodeBase64(str);			
				byte[] actual = decodeBase64(signature);
				return equals(expected, actual);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			return false;
    }

    private byte[] computeSignature(String baseString)
            throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKey key = null;
        synchronized (this) {
            if (this.key == null) {
                String keyString = OAuthUtils.percentEncode(getClientSecret());
//                        + '&' + OAuthUtils.percentEncode(getAccessToken());
                byte[] keyBytes = keyString.getBytes(ENCODING);
                this.key = new SecretKeySpec(keyBytes, MAC_NAME);
            }
            key = this.key;
        }
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(key);
        byte[] text = baseString.getBytes(ENCODING);
        return mac.doFinal(text);
    }
    
    /** ISO-8859-1 or US-ASCII would work, too. */
    private static final String ENCODING = OAuthUtils.ENCODING;

    private static final String MAC_NAME = "HmacSHA1";

    private SecretKey key = null;

}
