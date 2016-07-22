package com.nforum.platform.remotecache;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.commons.property.PropertyManager;

/**
 * Configures to RemoteCacheFactort real/local cache client depending on a boolean set in property file
 * Also implements propertyChangeListsner so that this can be changed at runtime
 * 
 *
 */
@Component
public class CacheClientConfigurer implements PropertyChangeListener {
	
	private Logger logger = Logger.getLogger(CacheClientConfigurer.class);

	@Autowired private PropertyManager properties;
	
	@Autowired
	@Qualifier("cacheClientReal")
	private CacheClient cacheClientReal;
	
	@Autowired
	@Qualifier("cacheClientLocal")
	private CacheClient cacheClientLocal;
	
	@PostConstruct
	private void setCacheClient()
	{
		if(properties.getPropertyAsBoolean("use.local.mem.cache"))
		{
			logger.info("Using local memory based cache client");
			RemoteCacheFactory.setCacheClient(cacheClientLocal);
		}
		else
		{
			logger.info("Using mem cache based cache client");
			RemoteCacheFactory.setCacheClient(cacheClientReal);
		}
	}
	
	@Override
	public void propertyChanged() {
		logger.info("Cache client reset requested");
		setCacheClient();
	}

	//package level access for testing only
	void setProperties(PropertyManager properties)
	{
		this.properties =properties;
	}
}
