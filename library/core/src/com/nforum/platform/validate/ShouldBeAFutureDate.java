package com.nforum.platform.validate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nforum.platform.exception.ValidationException;

public class ShouldBeAFutureDate implements Validator {
	
	String dateFormat;
	
	public ShouldBeAFutureDate(String dateFormat) {
		super();
		this.dateFormat = dateFormat;
	}

	@Override
	public void validate(Object obj) {
		Date date =null;
		if(obj==null){
			throw new ValidationException("Date is null");
		}
		
		if(obj instanceof Date){
			date = (Date) obj;
		}else if(obj instanceof String){
			try{
	    		DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
	    		date = dateFormatter.parse((String)obj);
	    	}catch (ParseException e) {
	    		throw new ValidationException("Invalid date format" + obj);
			}
		}else{
			throw new ValidationException("Future date typeis not valid" + obj);
		}
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		Calendar calCurrent = Calendar.getInstance();
		if(calDate.before(calCurrent)){
			if(!(calDate.get(Calendar.DATE)==calCurrent.get(Calendar.DATE) 
					&& calDate.get(Calendar.MONTH) == calCurrent.get(Calendar.MONTH)
					&& calDate.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR))){
				throw new ValidationException("Not a valid future date" + obj);
			}
		}
	}
}


