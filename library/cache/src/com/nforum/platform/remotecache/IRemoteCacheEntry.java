package com.nforum.platform.remotecache;

import java.io.Serializable;
/**
 * An interface for a remote cache entry object. Each object in the remote cache is encapsulated in this 
 * implementation
 */
public interface IRemoteCacheEntry extends Serializable{
	/**
	 * Gets the key of the entry
	 * @return the cache key to the caller
	 */
	public String getKey();
	/**
	 * Retrieves the object that was cached with the specified key
	 * @param key the cache key object
	 * @param url the url of the cache server
	 * @return the actual object instance
	 */
	public Object getObject();
	/**
	 * Gets the time out interval of the cache object
	 * @return time out interval in seconds
	 */
	public int getTimeOutInterval();
	
	public String getURL();
	public Long getCacheFetchTimeout();
}
