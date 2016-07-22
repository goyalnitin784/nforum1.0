package com.nforum.platform.util.date;


import java.util.Calendar;

/*
 * To interpret Hour
 * @author Ravi
 */

public class HourInterpretter {

	int hourValue;
	
	public String interpretHour(String hourIn)
	{
		return interpretHour(hourIn,"H");
	}
	
	/**
	 * @param hourIn
	 * @param defaultHour
	 * @return
	 */
	private String interpretHour(String hourIn, String defaultHour) {
		if(hourIn == null)
			hourIn = defaultHour;
		
		Calendar now = null;
		
		if(hourIn.equals("H"))
		{
			 now = Calendar.getInstance();
			 hourValue = now.get(Calendar.HOUR_OF_DAY);
		}

   
		else if(hourIn.startsWith("H-"))
		{
			int n = Integer.parseInt(hourIn.substring(2));
			n = 0-n;
			now = Calendar.getInstance();
			now.add(Calendar.HOUR, n);
			hourValue = now.get(Calendar.HOUR_OF_DAY);
		}
		
		
		else if(hourIn.startsWith("H+"))
		{
			int n = Integer.parseInt(hourIn.substring(2));
			now = Calendar.getInstance();
			now.add(Calendar.HOUR, n);
			hourValue = now.get(Calendar.HOUR_OF_DAY);
			
		}
		
		else
		{
			now = Calendar.getInstance();
			hourValue = Integer.parseInt(hourIn.substring(1,hourIn.length()));
		}
		
		
		if(now!=null)
			return String.valueOf(hourValue);
		else
			return hourIn; // to change n check for null condition

	}
	
}
