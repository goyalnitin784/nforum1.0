package com.nforum.platform.remotecache;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.commons.property.PropertyManager;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;

@Component("cacheClientReal")
public final class CacheClientImpl implements CacheClient, PropertyChangeListener {

	private static final Logger logger = Logger.getLogger(CacheClientImpl.class);
	private Map<String,MemcachedClient> cacheClientMap = null;

	@Autowired
	PropertyManager propertyManager;

	@PostConstruct
	public void init()
	{
		cacheClientMap = new HashMap<String, MemcachedClient>();
	}

	@Override
	public Object get(String key,String url, Long timeOutInMs) {
		Object entry= null;
		Future<Object> f = null;
		try {
			StringBuffer keyPrefix = new StringBuffer(
					propertyManager.getProperty("cache.prefix.product.type"));
			keyPrefix.append(key);
			MemcachedClient  memcachedClient = cacheClientMap.get(url);
			if(memcachedClient == null){
				List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>();
				for(String urlToken:url.split(",")){
					String[] tokens = urlToken.split(":");
					String host = tokens[0];
					Integer port = Integer.valueOf(tokens[1]);
					addresses.add(new InetSocketAddress(host,port));
				}
				memcachedClient = new MemcachedClient(addresses);
				cacheClientMap.put(url,memcachedClient);
			}
			if(timeOutInMs != null && timeOutInMs !=0){
				f = memcachedClient.asyncGet(keyPrefix.toString());
				entry = f.get(timeOutInMs, TimeUnit.MILLISECONDS);
			} else{
				entry= memcachedClient.get(keyPrefix.toString());
			}
		} catch (TimeoutException e) {
			logger.error("Memcache get timedout for key : " + key + " for url : " + url + " with timeout " + timeOutInMs);
			if(f != null){
				f.cancel(true);
			}
			return null;
		}catch (Exception ex) {
			logger.error("Remote Cache Not Available", ex);
			return null;
		}
		return entry;
	}

	@Override
	public Future<Boolean> set(IRemoteCacheEntry entry) {
		String key = entry.getKey();
		StringBuffer keyPrefix = new StringBuffer(
				propertyManager.getProperty("cache.prefix.product.type"));
		keyPrefix.append(key);

		MemcachedClient memcachedClient = cacheClientMap.get(entry.getURL());
		try {
			if(memcachedClient == null){
				List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>();
				for(String urlToken:entry.getURL().split(",")){
					String[] tokens = urlToken.split(":");
					String host = tokens[0];
					Integer port = Integer.valueOf(tokens[1]);
					addresses.add(new InetSocketAddress(host,port));
				}
				memcachedClient = new MemcachedClient(addresses);
				cacheClientMap.put(entry.getURL(),memcachedClient);
			}
			SerializingTranscoder coder = new SerializingTranscoder();
			coder.setCompressionThreshold(propertyManager.getPropertyAsInt("cache.compression.threshold.in.bytes"));
			return memcachedClient.set(keyPrefix.toString(), entry.getTimeOutInterval(),entry.getObject(),coder);
		} catch (Throwable ex) {
			logger.error("Remote Cache Not Available", ex);
			return null;
		}
	}



	@Override
	public void propertyChanged(){
		logger.debug("Disconnecting old memcache clients...");
		cacheClientMap = new HashMap<String, MemcachedClient>();
	}

	/*public static void main(String args[]) throws Exception{
		CacheClientImpl cache = new CacheClientImpl();
		IRemoteCacheEntry entry = new CacheEntryImpl();
		int timeout = 3000;
		String url = "10.224.73.43:11211";
		int i =0;
		final String key = "msg3_";
		final String value = "hello ";
		while(true){
			entry.set(key+i, value+i, timeout, url);
			Future<Boolean> result = cache.set(entry);
			Boolean isCachePut = true;
			isCachePut = result.get();
			int attempt = 0;
			while(!isCachePut){
				if(attempt == 3){
					throw new Exception("All 3 attempts failed to put in cache, there is something wrong");
				}
				logger.debug("Failed to put "+(key+i)+" retrying...");
				result = cache.set(entry);
				isCachePut = result.get();
				attempt++;
			}
			Object msg = RemoteCacheFactory.get(key+i,url);
			logger.debug("msg = "+msg);
			Thread.sleep(50);
			i++;
		}
	}*/
}
