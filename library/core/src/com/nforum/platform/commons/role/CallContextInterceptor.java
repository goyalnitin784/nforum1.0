
package com.nforum.platform.commons.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.commons.utils.RequestUtils;
import com.nforum.platform.commons.utils.StaticResourcesIdentifier;
import com.nforum.platform.util.HashingUtils;

public class CallContextInterceptor extends HandlerInterceptorAdapter{

	
	@Autowired PropertyManager propertyManager;
	@Autowired HashingUtils hashingUtils;
	
	Logger logger = Logger.getLogger(CallContextInterceptor.class);

	public CallContextInterceptor() {
		super();
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


		CallContextInterface callContext = new CallContext();
		CallContextKeeper.setCallContext(callContext);
		
		//If static resource do nothing
		if(StaticResourcesIdentifier.isResourceStatic(request)) {
	 		return true;
		}
 		
 		callContext.addContextParam(ContextParams.ClientIpAddress,RequestUtils.getClientAddress(request));
 		callContext.addContextParam(ContextParams.HostIpAddress, request.getLocalAddr());
 		callContext.addContextParam(ContextParams.Headers,RequestUtils.getHeaders(request));
 		callContext.addContextParam(ContextParams.UserAgentInfo, request.getHeader("User-Agent"));
 		
 		return true;

	 }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
            throws Exception {
		CallContextKeeper.setCallContext(null);
		response.addHeader("X-UA-Compatible", "IE=Edge");
	}

}
