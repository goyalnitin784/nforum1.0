package com.nforum.platform.remotecache;

import java.util.concurrent.Future;

public interface CacheClient {

	Object get(String key, String url, Long timeOutInMs);

	Future<Boolean> set(IRemoteCacheEntry entry);

}
