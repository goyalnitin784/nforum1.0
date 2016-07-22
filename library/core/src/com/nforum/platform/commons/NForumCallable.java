package com.nforum.platform.commons;

import java.util.concurrent.Callable;

public abstract class NForumCallable<RES> extends NForumThread implements Callable<RES> {
	
	@Override
	public RES call() throws Exception {
		
		RES res = null;
		threadPreProcess();
		res = myCall();
		threadPostProcess();
		return res;
	}
	
	abstract public RES myCall()  throws Exception;
}
