package com.nforum.platform.remotecache;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.util.NForumUtil;

/**
 * A class to handle cache functionality.
 */
@Component
public class CacheHandler {
    private static final Logger logger = Logger.getLogger(CacheHandler.class);

    @Autowired
    private PropertyManager propertyManager;

    /**
     * Checks if cache search enabled.
     *
     * @param propertyKey the property key
     * @return true, if cache search enabled
     */
    public boolean isCacheOn (String propertyKey) {
        boolean searchCacheEnabled = Boolean.valueOf(propertyManager.getProperty(propertyKey));
        if(searchCacheEnabled==false)
        	searchCacheEnabled = Boolean.valueOf(propertyManager.getProperty("cache." + propertyKey));
        return searchCacheEnabled;
    }


    /**
     * Mark stale to the cache in case of change in the content.
     *
     * @param cacheOnPropName the cache on property name
     * @param urlPropName the URL property name
     * @param ttlPropName the timeToLive property name
     */
    public void markStale(String key,String cacheOnPropName,String urlPropName,String ttlPropName,String TimeoutPropName){
        set(key,cacheOnPropName,urlPropName,ttlPropName,CacheConstants.CONTENT_UPDATED_KEY,null, TimeoutPropName);
    }

    /**
     * Mark stale to the cache in case of change in the content.
     *
     * <P>
     *      Make sure to use below nomenclature in defining constant. <br/>
     *          URL - <CACHE_NAME>.cache.url <br/>
     *          Time To Live - <CACHE_NAME>.cache.inactive.interval.in.sec  <br/>
     *          Cache ON - cache.<CACHE_NAME>  <br/>
     * </P>
     *
     * @param key the key
     * @param cacheName the cache name
     */
    public void markStale(String key,String cacheName){
        markStale(key,getCacheOnPropName(cacheName)
                ,getUrlPropName(cacheName)
                ,getTtlPropName(cacheName),getSetTimeoutPropName(cacheName));
    }

    /**
     * Mark stale to the cache in case of change in the content of default Cache.
     *
     * @param key the key
     */
    public void markStale(String key){
        markStale(key,CacheConstants.DEFAULT_CACHE_NAME);
    }

    /**
     * Gets Object from cache.
     *
     * @param cacheOnPropName the cache on property name
     * @param key the key
     * @param urlPropName the url property name
     * @return the object
     */
    public Object get(String key,String cacheOnPropName,String urlPropName){
       return get(key, cacheOnPropName, urlPropName, CacheConstants.DEFAULT_CACHE_TIMEOUT);
    }


    public Object get(String key,String cacheOnPropName,String urlPropName, String timeoutPropName){
        Object data  = null;

        if (isCacheOn(cacheOnPropName)) {
            final String url = propertyManager.getProperty(urlPropName);
            Long timeout = propertyManager.getPropertyAsLong(timeoutPropName);
            logger.trace("Getting key: " + key +" and url: "+ url);
            data = RemoteCacheFactory.get(key, url, timeout);
        }

        if(data instanceof String &&
                CacheConstants.CONTENT_UPDATED_KEY.equalsIgnoreCase((String)data)){
            return null;
        }
        return data;
    }


    /**
     * Gets Object from cache.
     *
     * <P>
     *      Make sure to use below nomenclature in defining constant. <br/>
     *          URL - <CACHE_NAME>.cache.url <br/>
     *          Time To Live - <CACHE_NAME>.cache.inactive.interval.in.sec  <br/>
     *          Cache ON - cache.<CACHE_NAME>  <br/>
     * </P>
     *
     * @param key the key
     * @param cacheName the cache name
     * @return the object
     */
    public Object get(String key, String cacheName){
        return get(
        		key,
        		getCacheOnPropName(cacheName),
        		getUrlPropName(cacheName),
        		getGetTimeoutPropName(cacheName));
    }






	public String getString(String key, String cacheName)
    {
    	Object cachedObject = get(key, cacheName);
    	if(cachedObject!=null && cachedObject instanceof String)
    		return (String)cachedObject;
    	return null;
    }
    
    public Integer getInteger(String key, String cacheName){
    	
    	Object cachedObject = get(key, cacheName);
    	if(cachedObject!=null && cachedObject instanceof Integer)
    		return (Integer)cachedObject;
    	return 0;
    	
    }

    /**
     * Gets Object from default cache.
     *
     * @param key the key
     * @return the object
     */
    public Object get(String key){
        return get(key,CacheConstants.DEFAULT_CACHE_NAME);
    }

    /**
     * Sets Object in cache.
     *
     * @param cacheOnPropName the cache on property name
     * @param key the key
     * @param urlPropName the url property name
     * @param ttlPropName the timeToLive prop name
     * @param data the data
     */
    public void set(String key,String cacheOnPropName,String urlPropName,String ttlPropName,Object data, String ttl, String cacheFetchTimeoutPropName){
        if (isCacheOn(cacheOnPropName) && data!=null) {
            final String url = propertyManager.getProperty(urlPropName);
            final int timeToLive = NForumUtil.isNullOrEmpty(ttl) ? propertyManager.getPropertyAsInt(ttlPropName) : Integer.valueOf(ttl);
            Long cacheFetchTimeout =  propertyManager.getPropertyAsLong(cacheFetchTimeoutPropName);
            IRemoteCacheEntry cacheEntry = new CacheEntryImpl(key, data, timeToLive, url,cacheFetchTimeout);
           	RemoteCacheFactory.set(cacheEntry);
        }
    }

    /**
     * Sets object in cache.
     * <P>
     *      Make sure to use below nomenclature in defining constant. <br/>
     *          URL - <CACHE_NAME>.cache.url <br/>
     *          Time To Live - <CACHE_NAME>.cache.inactive.interval.in.sec  <br/>
     *          Timeout in reading - <CACHE_NAME>.cache.timeout.in.milliseconds <br/>
     *          Cache ON - cache.<CACHE_NAME>  <br/>
     * </P>
     */
    public void set(String key,String cacheName,Object data){
      set(key, cacheName, data, null);
    }


    public void set(String key,String cacheName,Object data,String ttl){
        set(	
        		key,
        		getCacheOnPropName(cacheName),
        		getUrlPropName(cacheName),
        		getTtlPropName(cacheName),
        		data,
        		ttl,
        		getSetTimeoutPropName(cacheName)
        	);
    }

    public void set(String key,Object data){
        set(key,CacheConstants.DEFAULT_CACHE_NAME,data);
    }


    public String getUrlPropName(String cacheName){
        return cacheName + CacheConstants.URL_SUFFIX;
    }


    public String getTtlPropName(String cacheName){
        return cacheName + CacheConstants.TTL_SUFFIX;
    }


    public String getCacheOnPropName(String cacheName){
        return CacheConstants.CACHE_ON_PREFIX + cacheName;
    }

    private String getGetTimeoutPropName(String cacheName) {
    	return cacheName + CacheConstants.GET_TIMEOUT_SUFFIX;
	}

    private String getSetTimeoutPropName(String cacheName) {
    	return cacheName + CacheConstants.SET_TIMEOUT_SUFFIX;
	}
    

	private String getTenantDisablePropName(String cacheName) {
		return cacheName + CacheConstants.SET_TIMEOUT_SUFFIX;
	}

}
