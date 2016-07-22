package com.nforum.platform.urlshortener;

/**
 * 
 * @author aggarwald
 *
 */
public interface IUrlProcessor {

    public String shortenUrl(String fullUrl);
	
	public String getProcessorIdentifier();
}
