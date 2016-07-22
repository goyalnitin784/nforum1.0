package com.nforum.platform.remotecache;

/**
 * A constants class to hold cache keys
 *
 */
public class CacheConstants {

    
    
	public static final String CONFIG_VALUE_CACHE_NAME="config.value";
    public static final String DEFAULT_CACHE_NAME="default";
    
    //SUFFIX String
    public static final String TTL_SUFFIX = ".cache.inactive.interval.in.sec";
    public static final String URL_SUFFIX = ".cache.url";
    public static final String GET_TIMEOUT_SUFFIX = ".cache.get.timeout.in.milliseconds";
    public static final String SET_TIMEOUT_SUFFIX = ".cache.set.timeout.in.milliseconds";
    public static final String DEFAULT_CACHE_TIMEOUT = "cache.timeout.default.milliseconds";

    //Prefix String
    public static final String CACHE_ON_PREFIX = "cache." ;
    public static final String CONTENT_UPDATED_KEY = "content_updated";
    
}
