package com.nforum.platform.http.type;

import com.nforum.platform.exception.ConnectionException;


public class HttpMethodTypeFactory {

	public static HttpMethodType getHttpMethodType(String methodType,String url,boolean isChunkedResponse)
	{
		if(methodType.equalsIgnoreCase("GET"))
			return new HttpGetMethodType(url);
		else if(methodType.equalsIgnoreCase("POST"))
			return new HttpPostMethodType(url,isChunkedResponse);
		
		throw new ConnectionException("HttpMethod type not supportted");

	}
}
