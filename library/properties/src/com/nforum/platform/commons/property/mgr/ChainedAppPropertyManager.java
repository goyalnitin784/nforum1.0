package com.nforum.platform.commons.property.mgr;

import java.util.List;

import com.nforum.platform.commons.property.PropertyManagerSimple;

public class ChainedAppPropertyManager implements PropertyManagerSimple{

	List<PropertyManagerSimple> propertyManagers;

	
	public ChainedAppPropertyManager(
			List<PropertyManagerSimple> propertyManagers) {
		this.propertyManagers = propertyManagers;
	}


	@Override
	public String getProperty(String key) {
		
		for(PropertyManagerSimple propertyManager : propertyManagers)
		{
			String propertyValue = propertyManager.getProperty(key);
			if(propertyValue !=null)
				return propertyValue;
		}
		
		return null;
	}
	
	
	
}
