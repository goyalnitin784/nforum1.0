package com.nforum.platform.commons.property.mgr;

import java.text.MessageFormat;

import com.nforum.platform.commons.property.FormatedPropertyManager;
import com.nforum.platform.commons.property.PropertyManagerSimple;
/**
 * A decorator around simple property manager that serves formatted strings based on 
 * simple strings returned
 *
 */
public class FormattedPropertyManagerDecorator implements
		FormatedPropertyManager {

	private PropertyManagerSimple simplePropertyManager;
	
	public FormattedPropertyManagerDecorator(PropertyManagerSimple simplePropertyManager)
	{
		this.simplePropertyManager = simplePropertyManager;
	}
	
	@Override
	public String getProperty(String key) {
		
		return simplePropertyManager.getProperty(key);
	}

	@Override
	public String getProperty(String key, Object ... args ) {
		return MessageFormat.format(getProperty(key), args);
	}

}
