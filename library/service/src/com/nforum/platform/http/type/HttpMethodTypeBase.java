package com.nforum.platform.http.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;


public abstract class HttpMethodTypeBase implements HttpMethodType {

	protected HttpMethod httpMethod;

	protected NameValuePair[] getNameValuePairs(Map<String,String> parameters)
	{
		List<NameValuePair> lstPairs= new ArrayList<NameValuePair>();
		for(String key:parameters.keySet())
		{
			String value = parameters.get(key);
			lstPairs.add(new NameValuePair(key,value));
		}
		return lstPairs.toArray(new NameValuePair[]{});
	}

	@Override
	public void addHeaders(Map<String, String> headers) {
		for(String key: headers.keySet())
		{
			httpMethod.addRequestHeader(key,headers.get(key));
		}

	}

}
