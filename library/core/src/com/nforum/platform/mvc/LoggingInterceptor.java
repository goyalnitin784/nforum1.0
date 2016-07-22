package com.nforum.platform.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggingInterceptor extends HandlerInterceptorAdapter{

	Logger logger = Logger.getLogger(LoggingInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
    	StringBuffer url = request.getRequestURL();
    	//log if given url does not end with an extension
    	if(!(
				url.length()>4 && url.charAt(url.length()-3)=='.' 
				|| 
				url.length()>5 && url.charAt(url.length()-4)=='.'
				|| 
				url.length()>6 && url.charAt(url.length()-5)=='.'
			))
    	logger.debug(request.getRequestURL() + "?" + request.getQueryString());
    	return true;
    }
}
