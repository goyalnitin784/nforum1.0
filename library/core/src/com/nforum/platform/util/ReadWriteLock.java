package com.nforum.platform.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Generic Lock class based on writer and readers
 * 
 * @author Vineet Kasat
 * 
 */

@Component
public class ReadWriteLock{

	  private static final Logger logger = Logger.getLogger(ReadWriteLock.class);
	  private int readers = 0;
	  private int writers = 0;
	  private int writeRequests = 0;

	  public synchronized void lockRead() throws InterruptedException{
	    while(writers > 0 || writeRequests > 0){
	    logger.info("Waiting for read lock");	
	    wait();
	    }
	    readers++;
	    //logger.info("Acquired Read Lock");	
	  }

	  public synchronized void unlockRead(){
	    readers--;
	    notifyAll();
	    //logger.info("Acquired Read Lock");
	  }

	  public synchronized void lockWrite() throws InterruptedException{
	    writeRequests++;

	    while(readers > 0 || writers > 0){
	      logger.info("Waiting for write lock");
	      wait();
	    }
	    writeRequests--;
	    writers++;
	   // logger.info("Acquired Write Lock");
	  }

	  public synchronized void unlockWrite() throws InterruptedException{
	    writers--;
	    notifyAll();
	    //logger.info("Released Write Lock");
	  }
	}
