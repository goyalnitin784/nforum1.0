package com.nforum.platform.util.date;

/*
 *  To interpret DAy and Hour
 *  @author Ravi
 */

public class DayHourInterpretter {

    String hourValue;
    String day;

    HourInterpretter hourInterpretter;
    DateInterpretter dateInterpretter;

	public String interpretDayHour(String dayHourIn)
	{
		return interpretDayHour(dayHourIn,"TH");
	}

	public String interpretDayHour(String dayHourIn, String defaultDayDate)
	{

		 day = dayHourIn.substring(0,dayHourIn.indexOf("H"));
		 hourValue = dayHourIn.substring(dayHourIn.indexOf("H"),dayHourIn.length());

		//if(dayHourIn.equalsIgnoreCase("TH") && dayHourIn !=null)
		//  return new SimpleDateFormat(DateFormat).format(cal.getTime())+ String.valueOf(hourValue);
		 hourInterpretter = new HourInterpretter();
		 dateInterpretter = new DateInterpretter();
		 return dateInterpretter.interpretDate(day)+ hourInterpretter.interpretHour(hourValue);
	}
}
