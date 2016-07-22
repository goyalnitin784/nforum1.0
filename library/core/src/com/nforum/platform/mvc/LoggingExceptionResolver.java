package com.nforum.platform.mvc;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.commons.utils.NForumSpringFactory;

public class LoggingExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger logger = Logger.getLogger(LoggingExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(
				  HttpServletRequest request,
				  HttpServletResponse response,
				  Object handler,
				  Exception ex)
	{
		ModelAndView model =  super.resolveException(request, response, handler, ex);
		if("ClientAbortException".equals(ex.getMessage()))
				return model;
				
		logger.error(ex.getMessage(),ex);
		String errorCode = UUID.randomUUID().toString();
		logger.error("Error Code: " + errorCode);
		model.getModel().put("errorCode", errorCode );
		model.getModel().put("showErrorCode", getProperties().getProperty("show.error.code.on.error.page"));
		model.getModel().put("exceptionMessage", getProperties().getProperty("server.side.error"));
		return model;
	}

	private PropertyManager getProperties() {
		return (PropertyManager)NForumSpringFactory.getBean("propertyManager");
	}
	
}
