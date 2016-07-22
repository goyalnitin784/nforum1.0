package com.nforum.platform.commons.utils;

import java.util.Stack;

import org.apache.log4j.Logger;

/**
 * A thread safe, multi level time measuring utility class.
 * 
 * To call it say: 
 * Timer.timeIt("Time taken in my suspected code area");
 * 
 * Then some where down the code say:
 * Timer.timeUp();
 * 
 * It will print time taken from last start point along with the message.
 * 
 * You can call Timer.timeIt() many times inside another child functions etc.
 * Only corresponding time durations will get printed
 * 
 * Many threads can call these functions, the timing calculations do not clash with each other.
 * 
 * There are more elegant/short cut ways of achieving this for example writing 
 * an aspect with around advice to all methods. But this method give quick and fine grained
 * time measurement.
 * 
 * This class itself is taking around 10 ms delay for its internal processing.
 * This is due to storing/retrieving timings in a stack.
 * This can be future improvement
 *   
 * @author goyaln
 *
 */
public class Timeit {

	private static Logger logger = Logger.getLogger(Timeit.class);
	private static ThreadLocal<Stack<StackEntry>> threadLocal = new ThreadLocal<Stack<StackEntry>>();
	
	private static Stack<StackEntry> getStack()
	{
		if(threadLocal.get() == null )
			threadLocal.set(new Stack<StackEntry>());

		return threadLocal.get();
	}
	
	public static void timeIt(String message)
	{
		StackEntry entry = new StackEntry(0,message);
		if(getStack().size()>0)
			populateTabIndex(entry,message);
		
		entry.setTime(System.currentTimeMillis());
		getStack().push(entry);
	}
	
	private static void populateTabIndex(StackEntry entry, String message)
	{
		StackEntry prevEntry = getStack().peek();
		
		String prevMessage = prevEntry.getMessage();
		int prevMessageHiphenIndex = prevMessage.indexOf("-");
		String prevPrefix= (prevMessageHiphenIndex!=-1) ?
				prevMessage.substring(0,prevMessageHiphenIndex):
					prevMessage;

		
		int currMessageHiphenIndex = message.indexOf("-");
		String currentPrefix= (currMessageHiphenIndex!=-1) ?
				message.substring(0,currMessageHiphenIndex):
					message;
				
		if(!currentPrefix.equals(prevPrefix))
		{
			entry.setTabIndex(prevEntry.getTabIndex()+1);
		}
		else
			entry.setTabIndex(prevEntry.getTabIndex());
	}
	
	public static void timeUp()
	{
		if(!getStack().isEmpty()){
	  	  long now = System.currentTimeMillis();
		  StackEntry stackEntry =  getStack().pop();
		  logger.debug(getTheseManyTabs(stackEntry.getTabIndex()) + "TIME: "+ stackEntry.getMessage() + ": " + (now - stackEntry.getTime()) + " milliseconds" );
		}
	}
	
	//Can be handy while writing performance unit tests or bench mark tests
	public static long timeTaken()
	{
		if(!getStack().isEmpty()){
		  	  long now = System.currentTimeMillis();
			  StackEntry stackEntry =  getStack().pop();
			  long timeTaken = now - stackEntry.getTime();
			  return timeTaken;
		}
		return 0;
	}
	
	private static String getTheseManyTabs(int count)
	{
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<count;i++)
			buffer.append("   ");
		return buffer.toString();
	}
	
	private static class StackEntry
	{

		long time;
		String message;
		int tabIndex;
		
		
		public int getTabIndex() {
			return tabIndex;
		}
		public void setTabIndex(int tabIndex) {
			this.tabIndex = tabIndex;
		}
		public StackEntry(long time, String message) {
			super();
			this.time = time;
			this.message = message;
			tabIndex=0;
		}
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public String getMessage() {
			return message;
		}
		
	}
}

