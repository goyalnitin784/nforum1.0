package com.nforum.platform.remotecache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

/**
 * keeps objects in a local hashmap instead of going to mem cache client
 * can be switched on for testing or when mem cache is having some issues
 *
 */
@Component("cacheClientLocal")
public class LocalCacheClientImpl implements CacheClient{

	Map<String,Object> localMemCache = new ConcurrentHashMap<String,Object>();

	@Override
	public Object get(String key, String url, Long timeOutInMs) {
		return localMemCache.get(key);
	}

	@Override
	public Future<Boolean> set(IRemoteCacheEntry entry) {
		localMemCache.put(entry.getKey(), entry.getObject());
		return dummyFuture;
	}

	private Future<Boolean> dummyFuture = new Future<Boolean>(){

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			return true;
		}

		@Override
		public boolean isCancelled() {
			return false;
		}

		@Override
		public boolean isDone() {
			return true;
		}

		@Override
		public Boolean get() throws InterruptedException,
				ExecutionException {
			return true;
		}

		@Override
		public Boolean get(long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException,
				TimeoutException {
			return true;
		}};

}
