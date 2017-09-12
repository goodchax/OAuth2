package com.iovereye.oauth2.common.signature;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import com.iovereye.oauth2.common.utils.OAuthUtils;

public class HMAC_MD5 extends OAuthSignatureMethod {

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
    	StringBuilder query = new StringBuilder();
	    query.append(getClientSecret());
	    query.append(baseString);
        query.append(getClientSecret());
        MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(query.toString().getBytes(ENCODING));  
		return md.digest();
    }
    
    /** ISO-8859-1 or US-ASCII would work, too. */
    private static final String ENCODING = OAuthUtils.ENCODING;

}
