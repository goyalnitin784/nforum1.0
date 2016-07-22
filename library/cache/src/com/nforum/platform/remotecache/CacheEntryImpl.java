package com.nforum.platform.remotecache;


/**
 * A memcached based implementation for a remote cache entry object. Each object in the remote cache is encapsulated in this 
 * implementation
 */

public class CacheEntryImpl implements IRemoteCacheEntry{

	private String key;
	private Object value;
	private int timeOutInterval;
	private String url;
	private Long cacheFetchTimeout;

	public CacheEntryImpl(String key, Object value, int timeOutInterval, String url, Long cacheFetchTimeout) {
        super();
        this.key = key;
        this.value = value;
        this.timeOutInterval = timeOutInterval;
        this.url = url;
        this.cacheFetchTimeout = cacheFetchTimeout;
    }
	
	public CacheEntryImpl(){
	    
	}

    private static final long serialVersionUID = 3252287100268487419L;

	/**
	 * Gets the time out interval of the cache object
	 * @return time out interval in milliseconds
	 */
	public int getTimeOutInterval() {
		return timeOutInterval;
	}

	/**
	 * Retrieves the object that was cached with the specified key
	 * @param key the cache key object
	 * @param url the url of the cache server
	 * @return the actual object instance
	 */
	public Object getObject() {	
		return this.value;		
	}

	/**
	 * Gets the key of the entry
	 * @return the cache key to the caller
	 */
	@Override
	public String getKey() {		
		return this.key;
	}
	
	/**
	 * Gets the url of the cache where this entry has been cached
	 * @return the cache url to the caller
	 */
	@Override
	public String getURL() {		
		return this.url;
	}

	public Long getCacheFetchTimeout() {
		return cacheFetchTimeout;
	}

	
}
