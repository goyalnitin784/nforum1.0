package com.nforum.platform.urlshortener.bitly.responseParser;

/**
 * 
 * @author aggarwald
 *
 */
public interface IBitlyResponseParser {

    public String parse(String response) throws Exception;
}

