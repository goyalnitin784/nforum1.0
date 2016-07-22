package com.nforum.platform.util;

import java.util.UUID;


public abstract class UrlConstructor {

	protected abstract String uniqueParameterName();
	
	protected String parameterPair(String key, String value)
	{
		return parameterPair(key,value,false);
	}
	
	protected String parameterPair(String key, String value, boolean firstParam)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(firstParam?"?":"&");
		sb.append(key);
		sb.append("=");
		sb.append(value);
		return sb.toString();
	}
	
	
	protected String uniqueParameterClause()
	{
		return  parameterPair(uniqueParameterName(), UUID.randomUUID().toString(),false);
	}
	
	protected String uniqueParameterClause(boolean firstParam)
	{
		return  parameterPair(uniqueParameterName(), UUID.randomUUID().toString(),firstParam);
	}
	
	
	
}
