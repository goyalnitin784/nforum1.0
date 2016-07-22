package com.nforum.platform.commons.property.mgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.commons.role.CallContextKeeper;

public class HostSpecificPropertyManager extends
ReloadableApplicationPropertyManager {
	
	@Autowired
	PropertyManager propertyManager;
	
	public HostSpecificPropertyManager(String propertyFileName) {
		super(propertyFileName);
		// TODO Auto-generated constructor stub
	}
	
	
	public String getNonNullProperty(String key) {
		return propertyManager.getNonNullProperty(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Integer getPropertyAsInt(String key) {
		return propertyManager.getPropertyAsInt(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Long getPropertyAsLong(String key) {
		return propertyManager.getPropertyAsLong(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Double getPropertyAsDouble(String key) {
		return propertyManager.getPropertyAsDouble(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Boolean getPropertyAsBoolean(String key) {
		return propertyManager.getPropertyAsBoolean(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Byte getPropertyAsByte(String key) {
		return propertyManager.getPropertyAsByte(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public String[] getPropertyAsStringArray(String key) {
		return propertyManager.getPropertyAsStringArray(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public Boolean[] getPropertyAsBooleanArray(String key) {
		return propertyManager.getPropertyAsBooleanArray(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}


	public Integer[] getPropertyAsIntArray(String key) {
		// TODO Auto-generated method stub
		return propertyManager.getPropertyAsIntArray(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}

	public String getProperty(String key, String defaultValue) {
		// TODO Auto-generated method stub
		return propertyManager.getProperty(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key, defaultValue);
	}
	
	public List<String> getPropertyAsList(String key) {
		return propertyManager.getPropertyAsList(CallContextKeeper.getCallContext().getHostIPAddress()+"."+key);
	}
	

}
