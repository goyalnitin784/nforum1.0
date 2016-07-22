package com.nforum.platform.commons;


public abstract class NForumRunable extends NForumThread implements Runnable {

	@Override
	public void run(){
		threadPreProcess();
		myRun();
		threadPostProcess();
	}
	
	abstract public void myRun();

}
