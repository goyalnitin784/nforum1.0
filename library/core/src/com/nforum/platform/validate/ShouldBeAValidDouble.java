package com.nforum.platform.validate;

import com.nforum.platform.exception.ValidationException;
import com.nforum.platform.util.NForumUtil;

public class ShouldBeAValidDouble implements Validator{
	@Override
	public void validate(Object obj) {
		if(obj instanceof String && !NForumUtil.isNullOrEmpty((String)obj))
		{
			try{
				Double.parseDouble((String)obj);
			}
			catch(NumberFormatException nfe){
				throw new ValidationException("Not a valid double" + obj,nfe);
			}
		}
		else if(obj instanceof Integer || obj instanceof Long)
			return;
		else
			throw new ValidationException("Double input is mandatory"  + obj);

	}
}
