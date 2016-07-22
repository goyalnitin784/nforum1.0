package com.nforum.platform.util.date;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Years;


public class DateUtils {

	private static final Pattern patternISO8601 = createPatternISO8601();
	private static DatatypeFactory datatypeFactory = null;
	//private static final logger logger = loggerFactory.getlogger(DateUtils.class);
	static {
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			////logger.error(e.getMessage());
		}
	}

	public static final String NDaysfromNow(int n, String format)
	{
		Calendar cal  = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	public static enum DateEnum {
		webDisplayDateFormat("E dd MMM yy"), xdistRequestFormat("yyyy-MM-dd"),
		bookingDateFormat("E MMM d hh:mm:ss z yyyy"), superPNRDateFormat("yyyy-MM-dd hh:mm:ss"),
		trackingRequestFormat("MM/dd/yyyy"),holdAndPayShowDateFormat("dd MMM yy"),
		relianceInsDateFormat("dd/MM/yyyy"),fltscheduleTabFormat("yyyyMMdd"),
		EddMMMyyyyFormat("E dd MMM yyyy"),
		twelveHourTimeFormat("hh:mm a"),
		twentyFourHourTimeFormat("HH:mm");

		private String value;

		DateEnum(String value) {
			this.value = value;
		}

		public String getDateEnumValue() {
			return this.value;
		}
		public static DateEnum fromValue(String value)
	    {
	        for(DateEnum dateEnum :DateEnum.values())
	        {
	            if(dateEnum.value.equals(value))
	            {
	                return dateEnum;
	            }
	        }
	        return bookingDateFormat;
	    }
	};

	public static Date DateFormat(String dtStr) {
		String[] dataStr = dtStr.split("/");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dataStr[2]), Integer.parseInt(dataStr[1]) - 1, Integer.parseInt(dataStr[0]));
		return new Date(cal.getTimeInMillis());
	}
	
	public static String DateFormat(String dtStr, DateEnum format) {
		Calendar cal = setCalenderdate(dtStr);
		SimpleDateFormat formatter = new SimpleDateFormat(format.getDateEnumValue());
		return formatter.format(new Date(cal.getTimeInMillis()));
	}
	
	public static Calendar setCalenderdate(String dtStr)
	{
		String[] dateTimeStr = dtStr.split(" ");
		Calendar cal = Calendar.getInstance();
		String[] dataStr = dateTimeStr[0].split("-");
		if(dateTimeStr.length == 1)
		{
			cal.set(Integer.parseInt(dataStr[0]), Integer.parseInt(dataStr[1]) - 1, Integer.parseInt(dataStr[2]));
		}else
		{
			String[] timeStr = dateTimeStr[1].split(":");
			cal.set(Integer.parseInt(dataStr[0]), Integer.parseInt(dataStr[1]) - 1, Integer.parseInt(dataStr[2]), Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]), Integer.parseInt(timeStr[2]));
		}
		
		return cal;
	}

	public static java.util.Date DateFormat(String dtStr,String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dtStr);
	}

	public static String customDateFormatter(String dataInput, DateEnum dateEnumInput, DateEnum dateEnumOutput) {
		SimpleDateFormat formatterInput = new SimpleDateFormat(dateEnumInput.getDateEnumValue());
		java.util.Date inputDate = null;
		try {
			inputDate = formatterInput.parse(dataInput);
		} catch (ParseException parseException) {
			//logger.error("Date format exception", parseException);
		}
		SimpleDateFormat formatterOutput = new SimpleDateFormat(dateEnumOutput.getDateEnumValue());
		String dataOutput = formatterOutput.format(inputDate);
		return dataOutput;
	}

	public static String customDateFormatter(Date dateInput, DateEnum dateEnumOutput) {
		SimpleDateFormat formatterOutput = new SimpleDateFormat(dateEnumOutput.getDateEnumValue());
		String dataOutput = formatterOutput.format(dateInput);
		return dataOutput;
	}

	public static String customDateFormatter(java.util.Date dateInput, DateEnum dateEnumOutput) {
		SimpleDateFormat formatterOutput = new SimpleDateFormat(dateEnumOutput.getDateEnumValue());
		String dataOutput = formatterOutput.format(dateInput);
		return dataOutput;
	}

	// diff between checkIn Date & checkout Date.
	public static String durationCalculator(String startDate, String endDate, DateEnum dateEnum) {
		return durationCalculatorByop(startDate, endDate, dateEnum.getDateEnumValue());
	}

	// diff between checkIn Date & checkout Date.
	public static String durationCalculatorByop(String startDate, String endDate, String formator) {
		java.util.Date stayDateStarts = new java.util.Date();
		java.util.Date stayDateEnds = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat(formator);
		try {
			stayDateStarts = formatter.parse(startDate);
			stayDateEnds = formatter.parse(endDate);
		} catch (ParseException e) {
			//logger.error("Date format exception", e);
		}
		Long daysOfStay = ((stayDateEnds.getTime() - stayDateStarts.getTime()) / (1000L * 60L * 60L * 24L));
		return daysOfStay.toString();
	}

	// diff. between current date & CheckIn Date
	public static String durationCalculator(String startDate, DateEnum dateEnum) {
		java.util.Date currentDate = new java.util.Date();
		java.util.Date stayDateEnds = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateEnum.getDateEnumValue());
		try {
			// stayDateStarts = formatter.parse(startDate);
			stayDateEnds = formatter.parse(startDate);
		} catch (ParseException e) {
			//logger.error("Date format exception", e);
		}
		Long daysOfStay = ((stayDateEnds.getTime() - currentDate.getTime()) / (1000L * 60L * 60L * 24L));
		return daysOfStay.toString();
	}

	// diff. between current date & Travel Date
	public static Long getNoOfDaysTillTravelDate(String travelDateString, DateEnum dateEnum) {
		java.util.Date currentDate = new java.util.Date();
		java.util.Date travelDate = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateEnum.getDateEnumValue());
		try {
			travelDate = formatter.parse(travelDateString);
		} catch (ParseException e) {
		}
		return ((travelDate.getTime() - currentDate.getTime()) / (1000L * 60L * 60L * 24L));
	}

	// gives the number of hours .between current date and travel date .
	
	public static Long getHoursTillTravelDate(String travelDateString, DateEnum dateEnum) {
		java.util.Date currentDate = new java.util.Date();
		java.util.Date travelDate = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateEnum.getDateEnumValue());
		try {
			travelDate = formatter.parse(travelDateString);
		} catch (ParseException e) {
			
		}
		return ((travelDate.getTime() - currentDate.getTime()) / (1000L * 60L * 60L));
	
	}
	
	//Change Date Format
	public static String changeFormat(String cnvDate,DateEnum inputFormat,DateEnum outputFormat){
		String newDate = "";

		SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat.getDateEnumValue());
		SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat.getDateEnumValue());
		try {
			java.util.Date tempDate = inputFormatter.parse(cnvDate);
			 newDate = outputFormatter.format(tempDate);
		} catch (ParseException e) {
		}
		return newDate;
	}

	public static String changeFormat(String cnvDate,String inputFormat,String outputFormat){
		DateEnum inputDateFormat = DateEnum.fromValue(inputFormat);
		DateEnum outputDateFormat = DateEnum.fromValue(outputFormat);
		String newDate = changeFormat(cnvDate, inputDateFormat, outputDateFormat);
		return newDate;
	}

	/**
	 *
	 * @param date
	 *            ; the string from which the XMLGregorianCalendar instance would be made
	 * @param dateEnum
	 *            ; the format of the date
	 * @return
	 */

	@SuppressWarnings("deprecation")
	public static XMLGregorianCalendar prepareXMLGregorianCalendar(String date, DateEnum dateEnum) {
		java.util.Date localDate = null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateEnum.getDateEnumValue());

		try {
			localDate = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			//logger.error("Date format exception", e);
			// e.printStackTrace();
			return null;
		}

		return datatypeFactory.newXMLGregorianCalendar(localDate.getYear(), localDate.getMonth(), localDate.getDay(),
				localDate.getHours(), localDate.getMinutes(), localDate.getSeconds(), 0, localDate.getTimezoneOffset());
	}

	@SuppressWarnings("deprecation")
	public static XMLGregorianCalendar prepareXMLGregorianCalendar(java.util.Date localDate) {

		return datatypeFactory.newXMLGregorianCalendar(localDate.getYear(), localDate.getMonth(), localDate.getDay(),
				localDate.getHours(), localDate.getMinutes(), localDate.getSeconds(), 0, localDate.getTimezoneOffset());
	}

	// diff between checkIn Date & checkout Date.
	public static Long durationCalculator(java.util.Date startDate, java.util.Date endDate) {
		long endTime = endDate.getTime();
		long startTime = startDate.getTime();
		Long diffLong = ((endTime - startTime) / (1000L * 60L * 60L * 24L));
		return diffLong;
	}

	public static Date defaultFormat(String dtStr) {
		String[] dataStr = dtStr.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dataStr[0]), Integer.parseInt(dataStr[1]) - 1, Integer.parseInt(dataStr[2]),0,0,0);
		return new Date(cal.getTimeInMillis());
	}

	public static boolean isSameDate(java.util.Date date1,java.util.Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
			if(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)){
				if(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)){
					return true ;
				}
			}
		}
		return false;
	}
	public static String now(String dateFormat) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());
	}

	 public static String changeDateByDays(String inputDate, int days) {
	    Calendar date1 = parseDate(inputDate);
	    date1.add(5, days);
	    return calendarToISO8601Date(date1);
	  }

	 public static String calendarToISO8601Date(Calendar date)
	  {
	    ISO8601 iso = new ISO8601();
	    iso.year = date.get(1);
	    StringBuffer sb = new StringBuffer();
	    sb.append(iso.year);
	    sb.append("-");
	    iso.month = (date.get(2) + 1);
	    if (iso.month < 10)
	      sb.append("0");
	    sb.append(iso.month);
	    sb.append("-");
	    iso.day = date.get(5);
	    if (iso.day < 10)
	      sb.append("0");
	    sb.append(iso.day);
	    sb.append("T");
	    iso.hour = date.get(11);
	    if (iso.hour < 10)
	      sb.append("0");
	    sb.append(iso.hour);
	    sb.append(":");
	    iso.min = date.get(12);
	    if (iso.min < 10)
	      sb.append("0");
	    sb.append(iso.min);

	    return sb.toString();
	  }

	 public static class StringUtilities{
		 public static int toInt(String x)
		  {
		    if (x == null) return 0;
		    try
		    {
		      return Integer.parseInt(x);
		    } catch (NumberFormatException e) {
		    }
		    return 0;
		  }
	 }


	 public static Calendar parseDate(String iso8601Date) {
	    ISO8601 iso = null;
	    try {
	      Matcher match = patternISO8601.matcher(iso8601Date);
	      if (match.matches())  {
	        iso = new ISO8601();
	        iso.year = StringUtilities.toInt(match.group(1));
	        iso.month = StringUtilities.toInt(match.group(3));
	        iso.day = StringUtilities.toInt(match.group(5));
	        iso.tz = match.group(14);
	      }
	    } catch (Exception ree)  {
	      iso = null;
	    }
	    if (iso != null) {
	      TimeZone tz = null;
	      if ((iso.tz != null) && (iso.tz.length() != 0)) {
	        if (iso.tz.equals("Z"))
	          tz = TimeZone.getTimeZone("GMT");
	        else if (iso.tz.length() == 3)
	          tz = TimeZone.getTimeZone(iso.tz);
	        else {
	          tz = TimeZone.getTimeZone("GMT" + iso.tz);
	        }
	      }
	      Calendar cal = new GregorianCalendar(iso.year, iso.month - 1, iso.day);
	      if (tz != null) {
	        cal.setTimeZone(tz);
	      }
	      return cal;
	    }
	    return null;
	  }

	 private static final Pattern createPatternISO8601() {
	    Pattern pattern = null;
	    try
	    {
	      pattern = Pattern.compile("(\\d\\d\\d\\d)(-(\\d\\d)(-(\\d\\d))?)?([T| ]?(\\d\\d):(\\d\\d)(:((\\d\\d)(\\.(\\d+))?)?)?(Z|([+-]\\d\\d:\\d\\d)|([A-Z]{3}))?)?");
	    }
	    catch (Throwable ex)  {
	    }
	    return pattern;
	  }

	 /**
	  *
	  * @param iso8601Date e.g "1987-12-23T12:56:34IST" or "1987-12-23T12:56:34Z" or "1987-12-23T12:56:34+05:30"
	  * @return Calendar Object
	  */
	 public  static Calendar convertStringToCalendar(String iso8601Date) {
		    ISO8601 iso = null;
		    try {
		      Matcher match = patternISO8601.matcher(iso8601Date);
		      if (match.matches())  {
		        iso = new ISO8601();
		        iso.year = StringUtilities.toInt(match.group(1));
		        iso.month = StringUtilities.toInt(match.group(3));
		        iso.day = StringUtilities.toInt(match.group(5));
		        iso.hour = StringUtilities.toInt(match.group(7));
		        iso.min = StringUtilities.toInt(match.group(8));
		        iso.sec = StringUtilities.toInt(match.group(11));
		        iso.tz = match.group(14);
		      }
		    } catch (Exception ree)  {
		      iso = null;
		    }
		    if (iso != null) {
		      TimeZone tz = null;
		      if ((iso.tz != null) && (iso.tz.length() != 0)) {
		        if (iso.tz.equals("Z"))
		          tz = TimeZone.getTimeZone("GMT");
		        else if (iso.tz.length() == 3)
		          tz = TimeZone.getTimeZone(iso.tz);
		        else {
		          tz = TimeZone.getTimeZone("GMT" + iso.tz);
		        }
		      }
		      Calendar cal = new GregorianCalendar(iso.year, iso.month - 1, iso.day,iso.hour,iso.min,iso.sec);
		      if (tz != null) {
		        cal.setTimeZone(tz);
		      }
		      return cal;
		    }
		    return null;
		  }

	 public static Date calendarToDate(Calendar cal) {
			return new Date(cal.getTimeInMillis());
		}



	 public static String getDurationDDHHMM(String fromDate, String toDate){

		 Calendar from = convertStringToCalendar(fromDate);
		 Calendar to = convertStringToCalendar(toDate);
		 return getDurationDDHHMM(calendarToDate(from), calendarToDate(to));
	 }

	 public static String getDurationHHMM(String fromDate, String toDate){

		 Calendar from = convertStringToCalendar(fromDate);
		 Calendar to = convertStringToCalendar(toDate);
		 return getDuration(calendarToDate(from), calendarToDate(to));
	 }
		//The Date Object will have the timezone as well
		//Duration will be like 2days 20hr 45min
		public static String getDurationDDHHMM(Date fromDate, Date toDate)
		{
			String duration="";
			long days=0,hours=0,min=0;
			long time = toDate.getTime()-fromDate.getTime();
			days = time/(24*60*60*1000);
			time = time-(days*(24*60*60*1000));
			hours = (time)/(3600*1000);
			time = time- hours*(3600*1000);
			min = (time/(60*1000));
			if (days<1){
				if (hours<1) duration = ""+min+"min";
				else duration=""+hours+"hr "+min+"min";
			}
			else duration=""+days+"days "+hours+"hr "+min+"min";
			return duration;  // DDHHMM format
		}

		//The Date Object will have the timezone as well
		//Duration will be like 28hr 45min
		//If 0 hours then duration is like 45min
		public static String getDuration(Date fromDate, Date toDate)
		{
			String duration="";
			long hours=0,min=0;
			long time = toDate.getTime()-fromDate.getTime();
			hours = (time)/(60*60*1000);
			time = time- hours*(60*60*1000);
			min = (int)(time/(60*1000));
			if (hours<1) duration = ""+min+"min";
			else duration=""+hours+"hr "+min+"min";
			return duration;  // HHMM format
		}

	 public static class ISO8601
	 {
	   public int year;
	   public int month;
	   public int day;
	   public int hour;
	   public int min;
	   public int sec;
	   public int frac;
	   public String tz;
	 }

	 public int getDaysDifference(Date from, Date to)
	 {
			return Days.daysBetween(
					new org.joda.time.DateTime(from.getTime()),
					new org.joda.time.DateTime(to.getTime()))
			.get(DurationFieldType.days());
	 }
	 
	 public static int getAgeInMonths(String dateOfBirth)
	 {
		 try{
		 SimpleDateFormat sdf = new SimpleDateFormat(DateEnum.relianceInsDateFormat.value);
		 java.util.Date dob = sdf.parse(dateOfBirth);
		 Calendar calendar = Calendar.getInstance();
		 java.util.Date currentDate = calendar.getTime();
			return Months.monthsBetween(
					new org.joda.time.DateTime(dob.getTime()),
					new org.joda.time.DateTime(currentDate.getTime()))
			.get(DurationFieldType.months());
		 }catch(ParseException pe){
			 pe.printStackTrace();
			 return 0;
		 }
	 }
	 
	 	 
	 public int getMinutesDifference(Date from, Date to)
	 {
			return Days.daysBetween(
					new org.joda.time.DateTime(from.getTime()),
					new org.joda.time.DateTime(to.getTime()))
			.get(DurationFieldType.minutes());

	 }

	 public static long getCurrentTimeInMiliseconds(){
		 return System.currentTimeMillis();
	 }

	public static String getDurationInColonNotation(String fromDate, String toDate)
	{
		 return getDurationInColonNotation(
				 convertStringToCalendar(fromDate).getTime(),
				 convertStringToCalendar(toDate).getTime());
	}

	public static String getDurationInColonNotation(java.util.Date from, java.util.Date to)
	{
		Interval interval = new Interval(
				new org.joda.time.DateTime(from.getTime()),
				new org.joda.time.DateTime(to.getTime()));
		Period period = interval.toPeriod();

		if(period.getDays()==0)
			return String.format("%02d:%02d", period.getHours(), period.getMinutes());
		else
			return String.format("%02d:%02d:%02d", period.getDays(),period.getHours(), period.getMinutes());
	}

	 public static java.util.Date addDaysToDate(int days, java.util.Date date){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, days);
		 return calendar.getTime();
	 }
	 public static java.util.Date getCurrentDateWithoutTime()
	 {
		 	Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.HOUR_OF_DAY, 0);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 0);
		    return cal.getTime();
	 }

	 public static java.util.Date YesterdayMidNight()
	 {
		 Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	 }

	 public static java.util.Date getMonthLastDate(java.util.Date date){
		 int month =Integer.parseInt(new SimpleDateFormat("MM").format(date));
		 int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(year,month,1);
		 calendar.add(Calendar.MONTH, 1);
		 calendar.add(Calendar.DATE, -1);
		 return calendar.getTime();
	 }

	 public static Long timeElapsedinMinutes(String timeStamp){
	    	SimpleDateFormat formatter = new SimpleDateFormat(DateEnum.superPNRDateFormat.getDateEnumValue());
	    	java.util.Date currentDate = Calendar.getInstance().getTime();
	    	java.util.Date lastDate = Calendar.getInstance().getTime();
	    	try{
	    		lastDate = formatter.parse(timeStamp);
	    	}catch(ParseException pe){
	    		pe.printStackTrace();
	    	}
	    	Long minutesElapsed = ((currentDate.getTime() - lastDate.getTime()) / (1000L * 60L));
	    	return minutesElapsed;
	    }

	 public static java.util.Date CurrentHour()
	 {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	 }
	 
	 public static String getMinimumTime(String actual, String estimate) {
		 	String[] actualArr = actual.split(" ");
		 	String[] estimateArr = estimate.split(" ");
			Calendar actualCal = Calendar.getInstance();
			Calendar estimateCal = Calendar.getInstance();
			String[] actualDate = actualArr[0].split("-");
			String[] actualtime = actualArr[1].split(":");
			String[] estimateDate = estimateArr[0].split("-");
			String[] estimateTime = estimateArr[1].split(":");
			
			actualCal.set(Integer.parseInt(actualDate[0]), Integer.parseInt(actualDate[1])-1, Integer.parseInt(actualDate[2]), Integer.parseInt(actualtime[0]), Integer.parseInt(actualtime[1]), Integer.parseInt(actualtime[2]));
			actualCal.set(Integer.parseInt(estimateDate[0]), Integer.parseInt(estimateDate[1])-1, Integer.parseInt(estimateDate[2]), Integer.parseInt(estimateTime[0]), Integer.parseInt(estimateTime[1]), Integer.parseInt(estimateTime[2]));
			
			if(actualCal.compareTo(estimateCal) < 0)
			{
				return actualCal.getTime().toString();
			}
			return estimateCal.getTime().toString();
		}

	 
	 public static boolean checkDateExistInTwoHours(int min, int max) {	
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			boolean result = false;
			try{
				Calendar cal1 = Calendar.getInstance();	
				Calendar cal2 = Calendar.getInstance();
				
				if(min>max)
				{
					if(hour<min && hour<max)
					{
						cal1.roll(Calendar.DAY_OF_MONTH, false);	
					}
					else
					{
						cal2.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
				
				cal1.set(Calendar.HOUR_OF_DAY, min);
				cal1.set(Calendar.MINUTE, 0);
				cal1.set(Calendar.SECOND, 0);
				
				cal2.set(Calendar.HOUR_OF_DAY, max);
				cal2.set(Calendar.MINUTE, 0);
				cal2.set(Calendar.SECOND, 0);		
	
				if(cal.compareTo(cal1)>=0 && cal.compareTo(cal2)<=0)
					result = true;
			}catch(Exception e)
			{
				return false;
			}
			return result;
		}
	 
	 public static Long getTimeElapsedInMinutes(java.util.Date fromDate){
	 	java.util.Date currentDate = Calendar.getInstance().getTime();
    	Long minutesElapsed = ((currentDate.getTime() - fromDate.getTime()) / (1000L * 60L));
    	return minutesElapsed;
	 }

	}
