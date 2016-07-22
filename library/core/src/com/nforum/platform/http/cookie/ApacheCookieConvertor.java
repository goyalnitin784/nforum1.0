package com.nforum.platform.http.cookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ApacheCookieConvertor {

	private static javax.servlet.http.Cookie[] emptyServletCookieArray = new javax.servlet.http.Cookie[]{};
	private static org.apache.commons.httpclient.Cookie[] emptyAcpaheCookieArray = new org.apache.commons.httpclient.Cookie[]{};
	
	public static javax.servlet.http.Cookie[] servletCookies(org.apache.commons.httpclient.Cookie[] cookies)
	{
		List<javax.servlet.http.Cookie> servletCookies 
			= new ArrayList<javax.servlet.http.Cookie>(cookies.length);
		for(org.apache.commons.httpclient.Cookie cookie:cookies)
			servletCookies.add(servletCookieFromApacheCookie(cookie));
		return servletCookies.toArray(emptyServletCookieArray);
	}

	public static org.apache.commons.httpclient.Cookie[] apacheCookies(javax.servlet.http.Cookie[] cookies)
	{
		List<org.apache.commons.httpclient.Cookie>apacheCookies 
			= new ArrayList<org.apache.commons.httpclient.Cookie>(cookies.length);
		
		for(javax.servlet.http.Cookie cookie:cookies)
			apacheCookies.add(apacheCookieFromServletCookie(cookie));
		return apacheCookies.toArray(emptyAcpaheCookieArray);
	}

	private static javax.servlet.http.Cookie servletCookieFromApacheCookie(
			org.apache.commons.httpclient.Cookie apacheCookie) {
		if (apacheCookie == null) {
			return null;
		}

		String name = apacheCookie.getName();
		String value = apacheCookie.getValue();

		javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
		value = apacheCookie.getDomain();
		if (value != null) {
			cookie.setDomain(value);
		}
		value = apacheCookie.getPath();
		if (value != null) {
			cookie.setPath(value);
		}
		cookie.setSecure(apacheCookie.getSecure());
		value = apacheCookie.getComment();
		if (value != null) {
			cookie.setComment(value);
		}
		cookie.setVersion(apacheCookie.getVersion());

		Date expiryDate = apacheCookie.getExpiryDate();
		if (expiryDate != null) {
			long maxAge = (expiryDate.getTime() - System.currentTimeMillis()) / 1000;
			cookie.setMaxAge((int) maxAge);
		}

		return cookie;
	}

	public static org.apache.commons.httpclient.Cookie apacheCookieFromServletCookie(
			javax.servlet.http.Cookie cookie) {
		if (cookie == null) {
			return null;
		}

		org.apache.commons.httpclient.Cookie apacheCookie = null;

		String domain = cookie.getDomain();
		String name = cookie.getName();
		String value = cookie.getValue();
		String path = cookie.getPath();
		int maxAge = cookie.getMaxAge();
		boolean secure = cookie.getSecure();
		apacheCookie = new org.apache.commons.httpclient.Cookie(domain, name,
				value, path, maxAge, secure);
		apacheCookie.setComment(cookie.getComment());
		apacheCookie.setVersion(cookie.getVersion());
		return apacheCookie;
	}

	public org.apache.commons.httpclient.Cookie[] getHttpClientCookies(
			javax.servlet.http.Cookie[] cookies) {

		int numberOfCookies = 0;
		if (cookies != null) {
			numberOfCookies = cookies.length;
		}
		org.apache.commons.httpclient.Cookie[] httpClientCookies = new org.apache.commons.httpclient.Cookie[numberOfCookies];
		for (int i = 0; i < numberOfCookies; i++) {
			javax.servlet.http.Cookie c = cookies[i];
			String domain = c.getDomain();
			String name = c.getName();
			String value = c.getValue();
			String path = c.getPath();
			boolean secure = c.getSecure();
			int maxAge = c.getMaxAge();
			org.apache.commons.httpclient.Cookie hCookie = new org.apache.commons.httpclient.Cookie(
					domain, name, value, path, maxAge, secure);
			httpClientCookies[i] = hCookie;
		}
		return httpClientCookies;
	}


}
