package com.nforum.platform.urlshortener.bitly.responseParser;

import com.nforum.platform.urlshortener.bitly.responseParser.impl.BitlyJsonResponseParser;
import com.nforum.platform.urlshortener.bitly.responseParser.impl.BitlyXmlResponseParser;

/**
 * 
 * @author aggarwald
 *
 */
public class BitlyResponseParserFactory {
    
    public static IBitlyResponseParser getResponseParser(String format) {
        if (format != null) {
            if (format.equalsIgnoreCase(ResponseFormatEnum.XML.toString())) {
                return new BitlyXmlResponseParser();
            } else if (format.equalsIgnoreCase(ResponseFormatEnum.JSON.toString())) {
                return new BitlyJsonResponseParser();
            }
        }   
        return null;
    }

}
