package com.nforum.platform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.commons.utils.NForumSpringFactory;

public class NForumUtil {


	public static String[] setToArray(Set<String> set){
		int length= set.size();
		String[] strArray=new String[length];
		int i=0;
		for(String str : set){
			strArray[i]=str;
			i++;
		}
		return strArray;
	}
	
	
	public static Date addDaysToDate(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
	public static double roundOff(double value,int decimalDigits){
		if(decimalDigits==0){
			return Math.round(value);
		}
		double multiplier=1.0d;
		for(int i=0;i<decimalDigits;i++){
			multiplier*=10.0;
		}
		return Math.round(value*multiplier)/multiplier;
		
		
	}

	public static Date addDaysToCurrentDate(int days){
		Date date = new Date();
		return addDaysToDate(date,days);
	}

	/*public static String getCountryCode(String destinationCode) {
		MasterDataBean masterDataBean = (MasterDataBean)nforumSpringFactory.getBean("masterDataBean");
		if(!isNullOrEmpty(destinationCode)){
			try{
				String countryCode = masterDataBean.getCityMap().get(destinationCode).getCountryCode();
				if(isNullOrEmpty(countryCode)){
					return null;
				}
				else{
					return countryCode;
				}
			}catch(Exception e){
			    return null;
			}
		}
		else{
			return null;
		}
	}*/


	public static String convertToJsonCompatileXML(String rawString){
		String processedString = rawString.replace("\"", "'");
		processedString = processedString.replace("\\", "");
		return processedString;
	}

	public static String converDateToString(Date date, String format){
		DateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(date);
	}

	public static Date converStringToDate(String strDate, String format) throws ParseException{
		DateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.parse(strDate);
	}

	public static String changeDateFormat(String datestr, String orgFormat, String finalFormat) throws ParseException{
		DateFormat dateFormatter = new SimpleDateFormat(orgFormat);
		Date date = dateFormatter.parse(datestr);
		dateFormatter = new SimpleDateFormat(finalFormat);
		return dateFormatter.format(date);

	}

	public static String getStringValue(Object object){
    	return object==null?null:object.toString();
    }

	public static String getSringValueFromList(Object object,int pos){
    	if(object==null){
    		return "";
    	}
    	@SuppressWarnings("unchecked")
		List<String> list = (List<String>)object;
    	return list.size()>pos?list.get(pos):"";

    }

	public static String getEmptyIfNull(String strIn){
    	return strIn==null?"":strIn;
    }

	public static boolean isNullOrEmpty(String str)
	{
		return 	str==null || "".equals(str);
	}
	
	public static boolean isNullOrEmptyJSON(JSONObject jsonObject){
		return 	jsonObject==null || jsonObject.isNullObject() || jsonObject.isEmpty();
	}
	
	public static boolean isNullOrEmptyJSON(JSONArray jsonArray){
		return 	jsonArray==null || jsonArray.isEmpty();
	}

	public static String[] tokenize(String toTokenize, String splitSeqinuence)
	{
		List<String> list = new ArrayList<String>();

		if(toTokenize!=null)
		{
			int splitIndex=toTokenize.indexOf(splitSeqinuence);
			while(splitIndex!=-1)
			{
				String token = toTokenize.substring(0,splitIndex);
				list.add(token);
				toTokenize = toTokenize.substring(splitIndex+1);
				splitIndex = toTokenize.indexOf(splitSeqinuence);
			}
			list.add(toTokenize);
		}
		return list.toArray(new String[]{});
	}

    public static String replaceString(String source, String replaceThis, String replaceWith ){
    	String result = source;
    	int isPresent = source.indexOf(replaceThis);
    	if(isPresent != -1){
    		result = result.replaceAll(replaceThis, replaceWith);
    	}
    	return result;
    }

    public static String interpretPaymentResponseStatus(String responseRedirected)
    {
		String[] pieces = responseRedirected.split("&");
		for(String piece:pieces)
		{
			if(piece.contains("="))
			{
				String morePieces[] = piece.split("=");
				if(morePieces[0].equalsIgnoreCase("suc"))
					return morePieces[1];
			}
		}
		return "";
    }

    public  static <T,P> void  transferMember(Map<T,P> source, Map<T,P> target, T key)
    {
    	transferMember(source,target,key,null);
    }

    public  static <T,P> void  transferMember(Map<T,P> source, Map<T,P> target, T key,P defaultValue)
    {
    	if(source.containsKey(key))
    		target.put(key,source.get(key));
    	else if (defaultValue !=null)
    		target.put(key,defaultValue);
    }

    public static <T> T getFirstElementIfPresent(List<T> list)
    {
    	if(list!=null && list.size()>0)
    		return list.get(0);
    	else
    		return null;
    }

    public static <T> T getLastElementIfPresent(List<T> list)
    {
    	if(list!=null && list.size()>0)
    		return list.get(list.size()-1);
    	else
    		return null;
    }

	public static <T> String listToSingleLine(List<T> items, String separator) {
		if(items.size()==0)
			return "";

		StringBuffer sb = new StringBuffer();
		for(T s:items)
		{
			sb.append( s.toString() + separator);
		}
		return sb.substring(0,sb.length()-separator.length());

	}

	public static <T> String listToCSV(List<T> items) {
		return listToSingleLine(items,",");

	}
	public static String shrink(String str)
	{
		return str.replaceAll("\t", " ").replaceAll("\n", " ").replaceAll("\\s+", " ");
	}

	public static String fetchMatchedString(String string, Pattern pattern, Integer groupNo){
		if(string == null || pattern == null){
			return null;
		}
		Matcher m = pattern.matcher(string);
		String subString =null;
		if(m.find()){
			if(groupNo==null || groupNo==0){
				subString = m.group();
			}else{
				subString = m.group(groupNo);
			}

		}
		return subString;
	}

	public static List<String> fetchMatchedStrings(String string, Pattern pattern, Integer groupNo){
		if(string == null || pattern == null){
			return null;
		}
		Matcher m = pattern.matcher(string);
		List<String> subStrings = new ArrayList<String>();
		while(m.find()){
			if(groupNo==null || groupNo==0){
				subStrings.add(m.group());
			}else{
				subStrings.add(m.group(groupNo));
			}

		}
		return subStrings;
	}

	public static String escapeNewLine(String string) {
		string = string.replaceAll("\r\n"," ");
		return string.replaceAll("\n"," ");
	}
	public static String excapeApostrophe(String string)
	{
		return string.replaceAll("'", "&apos;");
	}

	public static PropertyManager getPropertyManager()
	{
		return (PropertyManager)NForumSpringFactory.getBean("propertyManager");
	}

	public static <T> Boolean isNullOrEmpty(List<T> list){
		if(list!=null && list.size()>0)
    		return false;
    	else
    		return true;
	}

	public static <T> Boolean isNullOrEmpty(T[] array){
		if(array!=null && array.length>0)
    		return false;
    	else
    		return true;
	}

	public static boolean isNullOrEmpty(Map<String, Object> map) {
		if(map != null & map.size() >0)
			return false;
		return true;
	}
	
	
	public static String formatDate2(String date){
		if(date==null || "".equals(date)){
    		return "";
    	}
	    	String newDate = "";
	    	try{
	    		newDate = changeDateFormat(date, "yyyy-MM-dd","dd/MM/yyyy");
	    	}catch (ParseException e) {
				e.printStackTrace();
			}
	    	return newDate;
	    }
}
