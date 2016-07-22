package com.nforum.platform.urlshortener.bitly.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nforum.platform.http.HttpService;
import com.nforum.platform.urlshortener.IUrlProcessor;
import com.nforum.platform.urlshortener.bitly.BitlyUrlProcessorConfig;
import com.nforum.platform.urlshortener.bitly.responseParser.BitlyResponseParserFactory;
import com.nforum.platform.urlshortener.bitly.responseParser.IBitlyResponseParser;

/**
 * Url shortener API by Bitly
 * @author aggarwald
 *
 */
@Service
public class BitlyUrlProcessorImpl implements IUrlProcessor {

    private static Logger logger = Logger.getLogger(BitlyUrlProcessorImpl.class);
    
    @Autowired
    @Qualifier("bitlyUrlProcessorConfig")
    private BitlyUrlProcessorConfig configuration;
    
    @Autowired
    @Qualifier("httpService")
    private HttpService httpService;
    
    private final static String PROCESSOR_IDENTIFIER = "BitlyUrlProcessor";
    
    @Override
    public String shortenUrl(String fullUrl) {
        logger.debug("Entering into BitlyUrlProcessorImpl.shortenUrl with fullUrl[" + fullUrl + "]" );
        String shortUrl = null;
        if (fullUrl != null) {
            try {
                String response = httpService.invoke(configuration.getSvcUrl(), getParamMap(fullUrl));
                logger.debug("Inside BitlyUrlProcessorImpl.shortenUrl with response[" + response + "]" );
                if (response != null) {
                    IBitlyResponseParser parser = BitlyResponseParserFactory.getResponseParser(configuration.getFormat());
                    if (parser != null) {
                        shortUrl = parser.parse(response);
                    }
                }              
            } catch (Exception e) {
                logger.error("Error in BitlyUrlProcessorImpl.shortenUrl [" + e.getCause() + "]" );
            } 
        }
        logger.debug("Exiting from BitlyUrlProcessorImpl.shortenUrl with shortUrl[" + shortUrl + "]" );
        return shortUrl;
    }
    
    @Override
    public String getProcessorIdentifier() {
        return PROCESSOR_IDENTIFIER;
    }
    
    private Map<String, String> getParamMap(String fullUrl) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("longUrl",fullUrl);
        parameters.put("version",configuration.getVersion());
        parameters.put("login",configuration.getLogin());
        parameters.put("apiKey",configuration.getApiKey());
        parameters.put("format",configuration.getFormat());
        parameters.put("history",configuration.getHistory());
        return parameters;
    }

}
