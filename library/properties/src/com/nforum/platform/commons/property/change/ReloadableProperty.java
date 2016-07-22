package com.nforum.platform.commons.property.change;

/**
 * Implementors are those who support propeties to be reloaded 
 * 
 * @author goyaln
 *
 */
public interface ReloadableProperty {

	public String getPropertyFileName();
	public String getPropertyFilePath();
	public void reload();
}
