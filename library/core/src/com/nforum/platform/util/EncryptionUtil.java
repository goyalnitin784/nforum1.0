package com.nforum.platform.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class EncryptionUtil {

	private static final Logger logger = Logger.getLogger(EncryptionUtil.class);
	
	
	public static String encrypt(String data){
		return encrypt(data,"MD5");
	}
	
	public static String encrypt(String data,String encryptionType){
		if(NForumUtil.isNullOrEmpty(data))
			return null;
	
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(encryptionType);
		} catch (NoSuchAlgorithmException e) {
			logger.debug("Exception in getting encrytor instance");
			return null;
		}
        md.update(data.getBytes());
        byte[] mdbytes = md.digest();
        
        //convert the byte to hex format
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<mdbytes.length;i++) {
    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
    	}
    	return hexString.toString();
	}
}
