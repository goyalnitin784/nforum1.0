package com.nforum.platform.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nforum.platform.commons.role.CallContextKeeper;
import com.nforum.platform.commons.role.ContextParams;

public class CookieUtils {

	public static final List<Cookie> EmptyCookieList = new ArrayList<Cookie>();
	
	public static Cookie getThisCookie(Cookie[] cookies,String cookieName)
	{
		if(cookies != null){
			
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName))
					return cookie;
			}
		}
		return null;
	}
	
	public static String getThisCookieValue(Cookie[] cookies,String cookieName)
	{
		Cookie cookie = getThisCookie(cookies, cookieName);
		return cookie!=null?cookie.getValue():null;
	}
	
	public static String getThisCookieValue(HttpServletRequest request,String cookieName)
	{
		Cookie cookie = getThisCookie(request.getCookies(), cookieName);
		
		//Look into parameters if can't find cookie value
		String cookieValue=null;
		if(cookie==null)
			cookieValue = request.getParameter(cookieName);
		else
			cookieValue = cookie.getValue();
		
		return cookieValue;
	}
	
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {        
        Cookie[] cookielist = request.getCookies();
        if(null != cookielist){
	        for (Cookie cookie: cookielist) {            
	        	if(name.equals(cookie.getName())){
	        		cookie.setMaxAge(0);
	        		cookie.setValue("");    
	        		cookie.setPath("/");
	        	    cookie.setDomain(".nforum.com");
	        		response.addCookie(cookie);
	            }
	        }
        }
    }
	
	public static void createCookie(HttpServletResponse response,String cookieName, String cookieValue,Integer maxAge, String path, String domain) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if(NForumUtil.isNullOrEmpty(path)){
			path = "/";
		}
		if(NForumUtil.isNullOrEmpty(domain)){
			domain = ".nforum.com";
		}
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setDomain(domain);
		response.addCookie(cookie);
		
		//Since this is a new cookie not prsent in original request header add it to CallContext for later use
		addCookieToCallContext(cookie);
		
	}

	private static void addCookieToCallContext(Cookie cookie) {
		
		Object objCookies = CallContextKeeper.getCallContext().getContextParam(ContextParams.AdditionalCookies);
		List<Cookie> lstCookies=null;
		if(objCookies==null)
			lstCookies = new ArrayList<Cookie>();
		else
			lstCookies = (List<Cookie>)objCookies;
		lstCookies.add(cookie);
		CallContextKeeper.getCallContext().addContextParam(ContextParams.AdditionalCookies,lstCookies);
	}
	
	public static void createCookie(HttpServletResponse response,String cookieName, String cookieValue) {
		createCookie(response, cookieName, cookieValue,31536000,null,null);    
	}
	
	public static void createSessionCookie(HttpServletResponse response,String cookieName, String cookieValue) {
		createCookie(response, cookieName, cookieValue,-1000,null,null);    
	}
	
	public static String createCookieIfNull(HttpServletRequest request,HttpServletResponse response,String cookieName, String cookieValue) {
		String cookieValueExisting = CookieUtils.getThisCookieValue(request,cookieName);
 		if(NForumUtil.isNullOrEmpty(cookieValueExisting))
 			cookieValueExisting = request.getParameter(cookieName);
 		if(NForumUtil.isNullOrEmpty(cookieValueExisting))
 		{
 			createCookie(response, cookieName, cookieValue,31536000,null,null);
 			return cookieValue;
 		}
 		else
 			return cookieValueExisting;
	}
}
