package com.nforum.platform.commons.role;

import java.util.HashMap;
import java.util.Map;


/**
 * Carrier object to keep user related data  
 *
 */
public class CallContext implements CallContextInterface{
	
	String sessionId;
	long userId;
	Map<String,Object> contextParams = new HashMap<String,Object>();
	
	@Override
	public String getSessionId() {
		return sessionId;
	}
	
	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public  String getLoginToken() {
		return getStringParam(ContextParams.LoginToken);
	}
	
	@Override
	public  void setLoginToken(String token) {
		contextParams.put(ContextParams.LoginToken, token);
	}
	
	@Override
	public void addContextParam(String key, Object value)
	{
		contextParams.put(key, value);
	}
	
	@Override
	public Object getContextParam(String key)
	{
		return contextParams.get(key);
	}

	@Override
	public Object getContextParam(String key,Object defaultValue)
	{
		return contextParams.get(key)==null?defaultValue:contextParams.get(key);
	}

	@Override
	public String getClientIPAddress() {
		return getStringParam(ContextParams.ClientIpAddress);
	}

	@Override
	public String getHostIPAddress() {
		return getStringParam(ContextParams.HostIpAddress);
	}
	
	@Override
	public String getUserAgentInfo() {
		return getStringParam(ContextParams.UserAgentInfo);
	}
	
	private String getStringParam(String key)
	{
		Object obj = contextParams.get(key);
		return obj==null?"":(String)obj;
	}
	
}
