package com.nforum.platform.validate;

import com.nforum.platform.exception.ValidationException;
import com.nforum.platform.util.NForumUtil;

public class ShouldBeAnIntegerGreaterThanZero implements Validator {

	@Override
	public void validate(Object obj) {
		if(obj instanceof String && !NForumUtil.isNullOrEmpty((String)obj))
		{
			try{
				int i = Integer.parseInt((String)obj);
				if(i<=0)
					throw new ValidationException("Number has to be graeter than zero" + obj);
			}
			catch(NumberFormatException nfe){
				throw new ValidationException("Not a valid integer" + obj,nfe);
			}
		}
		else if(obj instanceof Integer || obj instanceof Long)
			return;
		else 
			throw new ValidationException("Integer input is mandatory" + obj);
		
	}

}
