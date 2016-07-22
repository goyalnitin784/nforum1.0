package com.nforum.platform.util.convert;

import java.util.List;

public class ListToCSV implements Converter<List<String>, String> {

	
	private String separator =",";
	
	public ListToCSV(String separator)
	{
		this.separator = separator;
	}
	
	public ListToCSV(){}
	
	@Override
	public String convert(List<String> list) {
		
		StringBuilder sb = new StringBuilder();
		for(String s: list)
		{
			sb.append(s);
			sb.append(separator);
		}
		return sb.length()!=0?sb.substring(0,sb.length()-1):"";
	}

}
