package com.nforum.platform.commons.role;

import org.apache.log4j.Logger;


public class CallContextKeeper {

	private static final Logger logger = Logger.getLogger(CallContextKeeper.class);
	private static final ThreadLocal<CallContextInterface> threadLocal = new ThreadLocal<CallContextInterface>();
	static CallContextInterface callContext;
	
	public static CallContextInterface getCallContext() {
		CallContextInterface callContext =  threadLocal.get();
		if(callContext==null)
		{
			logger.warn("Null call context!");
			return new CallContext();
		}
		return callContext;
	}
	
	public static void setCallContext(CallContextInterface callContext) {
		threadLocal.set(callContext);
	}
	public static void removeCallContext(){
		threadLocal.remove();
	}
	
	static boolean callContextExists(){
		return getCallContext()!=null;
	}
	
	public static String getSessionId(){
		return callContextExists() ? getCallContext().getSessionId() : null;
	}
	
	public static Long getUserId(){
		Long userId = callContextExists() ? getCallContext().getUserId(): null;
		return userId == null || userId<=0? null : userId;
	}
	
	public static String getLoginToken(){
		String ssoToken = callContextExists() ? getCallContext().getLoginToken(): null;
		return ssoToken;
	}
}
