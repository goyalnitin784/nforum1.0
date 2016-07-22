package com.nforum.platform.commons.property.mgr;

import org.apache.log4j.Logger;

import com.nforum.platform.util.NForumUtil;

/**
 * This subclass of reloadable property manager is expected to return values
 * only when it exists in the same environment where it is expected to be.
 * 
 * For example we can wire this proper manager in RFS ahead in property chain
 * This was we can override RFS specific properties like payment amount.
 * This property manger will be inactive in other environments.
 *
 */
public class EnvironmentSpecificPropertyManager extends
		ReloadableApplicationPropertyManager {

	private static final Logger logger = Logger.getLogger(EnvironmentSpecificPropertyManager.class);
	private final boolean active;
	
	public EnvironmentSpecificPropertyManager(
			String propertyFileName,
			String currentEnvironment,
			String expectedEnvironment) {
		
		super(propertyFileName);
		
		if(!NForumUtil.isNullOrEmpty(currentEnvironment) && !NForumUtil.isNullOrEmpty(expectedEnvironment))
		{
			active = currentEnvironment.equalsIgnoreCase(expectedEnvironment);
		}
		else
			active = false;
		
		logger.info("Environment specific property manager is " + active +" for environment -" + currentEnvironment);
	}


	@Override
	public String getProperty(String key) {
		return active?super.getProperty(key):null;
	}
	
}
