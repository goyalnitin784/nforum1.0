package com.nforum.platform.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ValidationInterceptor extends HandlerInterceptorAdapter{
	
	private String applicationContextName;
	private Map<String,Validator> validatorMap;
	private static Logger logger = Logger.getLogger(ValidationInterceptor.class);
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
	            throws Exception {
		 String supposedValidator = getValidatorFromContextParameter(request);
		 Validator validator = supposedValidator==null ? null : validatorMap.get(supposedValidator);
		 if(validator!=null){
			 logger.trace("validating request for url :" + request.getRequestURL());
			 validator.validate(request);
		 }
		 return true;
	 }
	 
	 private String getValidatorFromContextParameter(HttpServletRequest request) {
			
			String uri = request.getRequestURI();
			if(applicationContextName!=null)
				uri = uri.substring(uri.indexOf(applicationContextName)+applicationContextName.length());
			List<String> uriParts = Arrays.asList(uri.split("/"));
			String supposedValidator=null;
			for(String string : validatorMap.keySet()){
				if(uriParts.contains(string)){
					supposedValidator=string;
					break;
				}
			}
			return supposedValidator;
		}

	public String getApplicationContextName() {
		return applicationContextName;
	}

	public void setApplicationContextName(String applicationContextName) {
		this.applicationContextName = applicationContextName;
	}

	public Map<String, Validator> getValidatorMap() {
		return validatorMap;
	}

	public void setValidatorMap(Map<String, Validator> validatorMap) {
		this.validatorMap = validatorMap;
	}
	 
	 

}
