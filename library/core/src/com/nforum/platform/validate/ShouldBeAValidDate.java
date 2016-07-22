package com.nforum.platform.validate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nforum.platform.exception.ValidationException;
import com.nforum.platform.util.NForumUtil;

public class ShouldBeAValidDate implements Validator {

	String dateFormat;
	
	public ShouldBeAValidDate(String dateFormat) {
		super();
		this.dateFormat = dateFormat;
	}

	@Override
	public void validate(Object obj) {
		if(obj instanceof String && !NForumUtil.isNullOrEmpty((String)obj))
		{
	    	try{
	    		DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
	    		dateFormatter.parse((String)obj);
	    	}catch (ParseException e) {
	    		throw new ValidationException("in-valid date format" + obj);
			}
		}
		else if(obj instanceof Date)
			return;
		else
			throw new ValidationException("Not a valide date" + obj);
	}

}
