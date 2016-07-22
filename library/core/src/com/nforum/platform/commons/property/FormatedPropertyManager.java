package com.nforum.platform.commons.property;

/**
 * interface to serve formatted properties 
 * @author goyaln
 *
 */
public interface FormatedPropertyManager extends PropertyManagerSimple {

	public String getProperty(String key, Object ... args);
}
