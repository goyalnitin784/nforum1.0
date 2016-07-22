package com.nforum.platform.util;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class UrlConstructorSimple extends UrlConstructor {

	@Override
	protected String uniqueParameterName() {
		return "unique";
	}

	public String construct(String url, Map<String, String> parameters) {

		String retUrl = url;
		if(parameters !=null && parameters.size()>0)
		{
			boolean firstParameter = NForumUtil.isNullOrEmpty(url) || url.contains("?")?false:true;
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			for(Entry<String,String> entry:parameters.entrySet())
			{
				sb.append(parameterPair(entry.getKey(), URLEncoder.encode(entry.getValue()), firstParameter));
				firstParameter=false;
			}
			retUrl =  sb.toString();
		}
		return retUrl;
	}

}
