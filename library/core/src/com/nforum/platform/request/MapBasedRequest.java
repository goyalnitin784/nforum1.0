package com.nforum.platform.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nforum.platform.commons.utils.RequestUtils;

public abstract class MapBasedRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327986489173488918L;
	protected Map<String,String> requestParameters;

	public MapBasedRequest(Map<String,String> parameterMap)
	{
		requestParameters = new HashMap<String, String>(parameterMap);
	}
	
	public MapBasedRequest() {
		requestParameters = new HashMap<String, String>();
	}

	public MapBasedRequest(HttpServletRequest httpRequest)
	{
		requestParameters = RequestUtils.getParamaters(httpRequest);
	}
	
	public String getParameter(String key)
	{
		return requestParameters.get(key);
	}
	
	public Map<String,String> getParameterMap()
	{
		return requestParameters;
	}

	@Override
	public String toString() {
		return requestParameters.toString();
	}

	protected String getParameter(String key, String defaultValue)
	{
		String str = getParameter(key);
		return str==null?defaultValue:str;
	}
	
}
