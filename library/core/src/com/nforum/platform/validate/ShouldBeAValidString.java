package com.nforum.platform.validate;

import com.nforum.platform.exception.ValidationException;
import com.nforum.platform.util.NForumUtil;

public class ShouldBeAValidString implements Validator {

	public static Validator Instance = new ShouldBeAValidString();
	
	public void validate(Object obj) {
		if(obj instanceof String && !NForumUtil.isNullOrEmpty((String)obj))
			return;
		else 
			throw new ValidationException("String input is mandatory" + obj);
	}

}
