package com.nforum.platform.commons.role;

/**
 * Carrier object to keep user related data
 *
 */
public interface CallContextInterface {

	public String getSessionId();

	public void setSessionId(String sessionId);

	public long getUserId();

	public void setUserId(long userId);

	public String getLoginToken();

	public void setLoginToken(String token);
	
	void addContextParam(String key, Object value);

	Object getContextParam(String key);

	Object getContextParam(String key, Object defaultValue);

	String getHostIPAddress();

	String getClientIPAddress();

	String getUserAgentInfo();

}
