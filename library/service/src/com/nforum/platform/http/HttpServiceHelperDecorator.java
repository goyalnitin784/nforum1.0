package com.nforum.platform.http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nforum.platform.util.Constants;
import com.nforum.platform.util.NForumUtil;

public class HttpServiceHelperDecorator {

	HttpService httpService;

	public HttpServiceHelperDecorator(HttpService httpService) {
		super();
		this.httpService = httpService;
	}
	
	public List<String> getCommaSeparatedResult(String url, Map<String,String> params)
	{
		try{
			String response = httpService.invoke(
					new HttpEndPoint(url,"GET"),params);
			
			if(!NForumUtil.isNullOrEmpty(response)) {
				return Arrays.asList(response.split(","));
			}
		}catch(Exception e){}
		return null;
	}
	
	
	public long getLongResult(String url, Map<String,String> params)
	{
		try{
			HttpEndPoint endPoint = new HttpEndPoint(url, "GET");
			String response = httpService.invoke(endPoint, params);
			if(!NForumUtil.isNullOrEmpty(response)) {
				return Long.parseLong(response);
			}
		}catch(Exception e){}
		return Constants.InvalidId;
	}
}
