package com.nforum.platform.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

@Component
public class HashingUtils {
	private final String HASHING_ALGORITHM = "SHA-1";
	private static Logger logger = Logger.getLogger(HashingUtils.class);
	
	public String getHashedValue(String data) {
		if (data == null)
			return null;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance(HASHING_ALGORITHM);

			md.update(data.getBytes());
			byte[] mdbytes = md.digest();

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				String hex = Integer.toHexString(0xFF & mdbytes[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception e){
			logger.error("error hashing the value");
			return data;
		}
	}
	
}
