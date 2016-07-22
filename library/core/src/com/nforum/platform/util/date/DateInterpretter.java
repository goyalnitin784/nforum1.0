package com.nforum.platform.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateInterpretter {

	public static String DateFormat = "yyyyMMdd";
	
	public String interpretDate(String dateIn)
	{
		return interpretDate(dateIn,"T");
	}
	
	public String interpretDate(String dateIn, String defaultDate)
	{
		if(dateIn == null)
			dateIn = defaultDate;
		
		Calendar cal = null;
		if(dateIn.equalsIgnoreCase("T"))
			cal = Calendar.getInstance();
		else if(dateIn.startsWith("T-"))
		{
			int n = Integer.parseInt(dateIn.substring(2));
			n = 0-n;
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, n);
		}
		else if(dateIn.startsWith("T+"))
		{
			int n = Integer.parseInt(dateIn.substring(2));
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, n);
		}
		
		if(cal!=null)
			return new SimpleDateFormat(DateFormat).format(cal.getTime());
		else
			return dateIn;
	}
	
	public Date getDate(String strDate)
	{
		try {
			return new SimpleDateFormat(DateInterpretter.DateFormat).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String addDate(String dateIn, int dtCount)
	{
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date parsed = dateFormat.parse(dateIn);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parsed);
			cal.add(Calendar.DATE, dtCount);
			return dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateIn;
	}
}
