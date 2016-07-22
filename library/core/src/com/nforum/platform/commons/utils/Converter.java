package com.nforum.platform.commons.utils;

import java.util.List;

public class Converter {

	public static String toStringCSV(List<String> items)
	{
		if(items.size()==0)
			return "";
		
		StringBuffer sb = new StringBuffer();
		for(String s:items)
		{
			sb.append("'" + s + "',");
		}
		return sb.substring(0,sb.length()-1);
	}
	
}
