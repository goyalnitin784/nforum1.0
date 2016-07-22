package com.nforum.platform.remotecache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
/**
 * A factory class to handle remote cache calls
 *
 */
public class RemoteCacheFactory {
	private static final Logger logger = Logger.getLogger(RemoteCacheFactory.class);
	private static CacheClient CACHE_CLIENT;

	public static void setCacheClient(CacheClient cacheClient)
	{
		CACHE_CLIENT = cacheClient;
	}

    public static Object get(String key,String url, Long timeOutInMs) {
		try {
			return CACHE_CLIENT.get(key,url, timeOutInMs);
		} catch (Exception e) {
			logger.error("Remote Cache Not Available",e);
		}
		return null;
	}

    public synchronized static void set(IRemoteCacheEntry entry){
        try{
			Future<Boolean> result = CACHE_CLIENT.set(entry);
			if(result!=null)
			{
				if(entry ==null)
					logger.error("entry is null");
				
				Boolean isCachePut = entry.getCacheFetchTimeout() ==null || entry.getCacheFetchTimeout()==0  ? 
							result.get() : 
							result.get(entry.getCacheFetchTimeout(), TimeUnit.MILLISECONDS);
						
				int attempt = 0;
				while(!isCachePut){
					if(attempt == 3){
					    logger.error("Memcache is not responding...");
					    break;
					}
					logger.debug("cache failed to put "+entry.getKey()+", retrying...");
					result = CACHE_CLIENT.set(entry);
					if(result==null)
						break;
					isCachePut = entry.getCacheFetchTimeout() ==null || entry.getCacheFetchTimeout()==0  ? 
							result.get() : 
							result.get(entry.getCacheFetchTimeout(), TimeUnit.MILLISECONDS);
					attempt++;
				}
			}
		} catch(Exception ee){
		    logger.error("Remote Cache Not Available",ee);
		}
	}

	//package protected for testing
	static CacheClient getCacheClient()
	{
		return CACHE_CLIENT;
	}

}
