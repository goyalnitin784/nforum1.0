package com.nforum.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharSetEncodingFilter implements Filter {
	
	   private static final String UTF8 = "UTF-8";  
	   private static final String CONTENT_TYPE = "text/html; charset=UTF-8";  

	   @Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	        response.setContentType(CONTENT_TYPE);  
	        response.setCharacterEncoding(UTF8);  
	        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
