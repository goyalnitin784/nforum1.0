package com.nforum.platform.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.exception.CustomException;
import com.nforum.platform.util.NForumUtil;

@Component
public class RequestUtils {

	@Autowired
	private PropertyManager propertyManager;

	public static boolean itIsAJSONRequest(Map<String, Object> searchCriteria) {
		return itIsAJSONRequest(searchCriteria.get("JSON"));
	}

	public static boolean itIsAJSONRequest(Object obj) {
		return obj != null && obj.toString().equalsIgnoreCase("JSON");
	}

	public String getParameterFromUrlEncodedUrl(String encodedUrl,
			String parameter) {
		try {
			String decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
			URL url = new URL(decodedUrl);
			String query = url.getQuery();
			String queryParts[] = query.split("&");
			for (String part : queryParts) {
				String expression[] = part.split("=");
				if (expression[0].equalsIgnoreCase(parameter)) {
					return expression[1];
				}
			}

		} catch (UnsupportedEncodingException e) {
			throw new CustomException(e);
		} catch (MalformedURLException e) {
			throw new CustomException(e);
		}
		return encodedUrl;
	}

	public String getValueFromCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String cookieName = propertyManager.getProperty(key);
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	public String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	public void removeCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		Cookie[] cookielist = request.getCookies();
		if (null != cookielist) {
			for (Cookie cookie : cookielist) {
				if (name.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath(propertyManager
							.getProperty("sessionCookiePath"));
					cookie.setDomain(propertyManager
							.getProperty("sessionCookieDomain"));
					cookie.setValue("");
					response.addCookie(cookie);
				}
			}
		}
	}

	public static Map<String, String> toMap(String queryString) {
		Map<String, String> map = new HashMap<String, String>();
		if (!NForumUtil.isNullOrEmpty(queryString)) {
			String[] nameValues = queryString.split("\\&");
			for (String nameValue : nameValues) {
				String keyValues[] = nameValue.split("=");
				if (keyValues != null && keyValues.length >= 2) {
					map.put(keyValues[0], keyValues.length > 1 ? keyValues[1]
							: "");
				}
			}
		}
		return map;
	}

	public static Map<String, String> getParamaters(HttpServletRequest request) {
		Map<String, String> requestParameters = new HashMap<String, String>();
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String fieldName = (String) e.nextElement();
			String fieldValue = request.getParameter(fieldName);
			Object[] fieldValues = request.getParameterValues(fieldName);
			if(fieldValues.length>1){
				for(Integer iter=0;iter<fieldValues.length;iter++){
					requestParameters.put(fieldName+"_"+iter, (String)fieldValues[iter]);
				}
			}else{
				requestParameters.put(fieldName, fieldValue);
			}
		}

		return requestParameters;
	}

	public static String getClientAddress(HttpServletRequest request) {
		
		String clientAddress = NForumUtil.isNullOrEmpty(request.getHeader("client-ip-address")) 
				? request.getRemoteAddr() 
				: request.getHeader("client-ip-address");
		
		String ipAddressListWithProxies = request.getHeader("X-FORWARDED-FOR");
		if (ipAddressListWithProxies != null) {
			clientAddress = ipAddressListWithProxies;
			int idx = clientAddress.indexOf(',');
			if (idx > -1) {
				clientAddress = clientAddress.substring(0, idx);
			}
		}
		return clientAddress;
	}

	public static String getHostIPAddress(HttpServletRequest request) {
		return request.getLocalAddr();
	}

	public void addCookie(HttpServletResponse response, String name,
			String value) {
		Cookie cookie = new Cookie(name, value);
		// cookie.setMaxAge(propertyManager.getPropertyAsInt(AppProperties.SESSION_COOKIE_AGE));
		cookie.setPath("/");
		cookie.setDomain(".nforum.com");
		response.addCookie(cookie);
	}
	
    public static Map<String, String> getHeaders(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        
        return map;
    }
    
    public static Map<String, String> getHeaders(HttpServletRequest request, boolean acceptEncoding) {

        Map<String, String> map = getHeaders(request);
        if(!acceptEncoding) {
        	map.remove("accept-encoding");
        }
        
        return map;
    }

}
