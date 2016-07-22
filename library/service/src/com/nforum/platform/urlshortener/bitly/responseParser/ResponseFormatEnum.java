package com.nforum.platform.urlshortener.bitly.responseParser;

/**
 * 
 * @author aggarwald
 *
 */
public enum ResponseFormatEnum {
    
    XML("xml"),
    
    JSON("json");
    
    String responseFormat;
    
    ResponseFormatEnum(String responseFormat) {
        this.responseFormat = responseFormat;
    }
    
    public String toString(){
        return responseFormat;
   }
    
}
