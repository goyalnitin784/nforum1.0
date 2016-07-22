package com.nforum.platform.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.nforum.platform.commons.role.CallContextKeeper;
import com.nforum.platform.commons.role.ContextParams;
import com.nforum.platform.util.NForumUtil;

public class TransferUtils {

	
	
	/**
	* Pass any headers in context scope ( that were passed to me ). 
	* This collection could be empty in case of stand alone client but would have data if the caller is itself a webapp
	* In second case the caller is supposed to forward headers like ssoToken to called web interface 
	* Also pass any additionally requested headers from this API's client 
	* 
	* @param additionalHeaders
	*/
	public static Map<String,String> getTransferHeaders(Map<String, String> additionalHeaders) {
	
		Map<String,String> headers = new HashMap<String, String>();
		
		boolean transferCookies = (null != additionalHeaders) 
				? !additionalHeaders.containsKey("X-NoCookie") : true;
		
		if(transferCookies)
		{
			//Transfer cookies
			String additionalCookieString= getAdditionalCookieString();
			String headerCookieString = getHeaderCookieString();
			String finalCookieString = getFinalCookieString(additionalCookieString,headerCookieString);
			if(!NForumUtil.isNullOrEmpty(finalCookieString))
			{
				headers.put("cookie", finalCookieString);	
			}
		}
		
		//Transfer and additional headers supplied by client
		if(additionalHeaders!=null)
			headers.putAll(additionalHeaders);
		
		setClientInfoHeaders(headers);

		return headers;
	}

	private static String getFinalCookieString(
			String cookieString1,
			String cookieString2) {
		
		/*
		 * Logic to check whether additional cookie is not implemented because additional cookie
		 * exists only because they were not present in original headers
		 */
		
		//If both are non empty concatenate
		if(!NForumUtil.isNullOrEmpty(cookieString1) && !NForumUtil.isNullOrEmpty(cookieString2))
			return cookieString1 +"; " + cookieString2;
		
		//Else send whichever is not null, if cookieString2 is also null, don't care (sending null anyway)
		return !NForumUtil.isNullOrEmpty(cookieString1)? cookieString1: cookieString2;
	}

	private static String getHeaderCookieString() {
		String headerCookieString = "";
		Object objContextHeaders = CallContextKeeper.getCallContext().getContextParam(ContextParams.Headers);
		if(objContextHeaders!=null)
		{
			Map<String,String> contextHeaders = (Map<String,String>) objContextHeaders;
			headerCookieString = contextHeaders.get("cookie");
			headerCookieString = headerCookieString==null? contextHeaders.get("Cookie") : headerCookieString; 
			headerCookieString = headerCookieString==null? contextHeaders.get("COOKIE") : headerCookieString;
		}
		return headerCookieString;
	}

	private static String getAdditionalCookieString() {
		String strAdditionalCookies="";
		Object objAdditionalCookies = CallContextKeeper.getCallContext().getContextParam(ContextParams.AdditionalCookies);
		if(objAdditionalCookies!=null)
		{
			List<Cookie> lstAdditionalCookies = (List<Cookie>)objAdditionalCookies;
			List<String> lstCookieNameValues = new ArrayList<String>();
			for(Cookie cookie:lstAdditionalCookies)
			{
				lstCookieNameValues.add(cookie.getName()+"="+cookie.getValue());
			}
			strAdditionalCookies =  NForumUtil.listToSingleLine(lstCookieNameValues, "; ");
		}
		return strAdditionalCookies;
	}
	
	private static void setClientInfoHeaders(Map<String, String> headers) {
		Object objContextHeaders = CallContextKeeper.getCallContext().getContextParam(ContextParams.Headers);
		String clientIpAddress = CallContextKeeper.getCallContext().getClientIPAddress();
		if(objContextHeaders!=null)
		{
			Map<String,String> contextHeaders = (Map<String,String>) objContextHeaders;
			headers.put("user-agent", contextHeaders.get("user-agent"));
			headers.put("client-ip-address", clientIpAddress);
		}
		return;
	}
	
}
