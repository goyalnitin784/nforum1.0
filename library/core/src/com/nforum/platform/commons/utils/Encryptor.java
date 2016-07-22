package com.nforum.platform.commons.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

	private static final String ALGO = "AES";
	
	public static String getMD5String(String inputString)
    {
    	String outputString = null;
    	StringBuffer hexString = null;
    	MessageDigest md5Digest;
		try {
			md5Digest = MessageDigest.getInstance("MD5");	
			md5Digest.reset();
			md5Digest.update(inputString.getBytes("UTF-8"));
			byte messageDigest[] = md5Digest.digest();		
            
			hexString = new StringBuffer();
			for (int i=0;i<messageDigest.length;i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if(hex.length()==1)
	            {
	                  hexString.append('0');
	            }
	            hexString.append(hex);
			}
			outputString = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return outputString;
    }
	
	public static String decrypt(String encryptedData, String strKey) throws Exception {
        Key key = generateKey(strKey);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new sun.misc.BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
	}

	private static Key generateKey(String strKey) {
		byte[] keyValue = strKey.getBytes();
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
}
