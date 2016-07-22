package com.nforum.platform.commons.property.mgr;

import java.io.File;

import org.apache.log4j.Logger;

import com.nforum.platform.commons.property.change.ReloadableProperty;

/**
 * An implementation of property manager that is reloadable as well
 * Reads properties from a configured property file
 * 
 * Note:Instances of this class are configured through conventional spring config files
 *
 */
public class ReloadableApplicationPropertyManager extends PropertyManagerSimpleImpl 
			implements ReloadableProperty {
	
	private Logger logger = Logger.getLogger(ReloadableApplicationPropertyManager.class);

	public ReloadableApplicationPropertyManager(String propertyFileName ){
		super(propertyFileName);
	}
	
	@Override
	public void reload() {
		logger.info("Application properties reload requested");
		loadProperties();
	}

	@Override
	public String getPropertyFileName() {
		return	new File(propertyFileName).getName();
	}

	@Override
	public String getPropertyFilePath() {
		return propertyFileName;
	}
}
