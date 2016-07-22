package com.nforum.platform.commons;

import com.nforum.platform.commons.role.CallContextInterface;
import com.nforum.platform.commons.role.CallContextKeeper;

public abstract class NForumThread {
	
	String tenant;
	CallContextInterface callContext;
	
	public NForumThread() {
		callContext = CallContextKeeper.getCallContext();
	}
	
	public void threadPreProcess(){
		if(callContext!=null){
			CallContextKeeper.setCallContext(callContext);
		}
	}
	
	public void threadPostProcess(){
		if(callContext!=null){
			CallContextKeeper.removeCallContext();
		}
	}
}
