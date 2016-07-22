package com.nforum.platform.urlshortener.tiny.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nforum.platform.http.HttpService;
import com.nforum.platform.urlshortener.IUrlProcessor;
import com.nforum.platform.urlshortener.tiny.TinyUrlProcessorConfig;

/**
 * Url shortener API by TinyUrl
 * @author aggarwald
 *
 */
@Service
public class TinyUrlProcessorImpl implements IUrlProcessor {

    private static Logger logger = Logger.getLogger(TinyUrlProcessorImpl.class);
    
    @Autowired
    @Qualifier("tinyUrlProcessorConfig")
    private TinyUrlProcessorConfig configuration;
    
    @Autowired
    @Qualifier("httpService")
    private HttpService httpService;
    
    private final static String PROCESSOR_IDENTIFIER = "TinyUrlProcessor";
    
    @Override
    public String shortenUrl(String fullUrl) {
        logger.debug("Entering into TinyUrlProcessorImpl.shortenUrl with fullUrl[" + fullUrl + "]" );
        String shortUrl = null;
        if (fullUrl != null) {
            try {
                shortUrl = httpService.invoke(configuration.getSvcUrl(), getParamMap(fullUrl));
            } catch (Exception e) {
                logger.error("Error in TinyUrlProcessorImpl.shortenUrl [" + e.getCause() + "]" );
            }
        }   
        logger.debug("Exiting from TinyUrlProcessorImpl.shortenUrl with shortUrl[" + shortUrl + "]" );
        return shortUrl;
    }
    
    @Override
    public String getProcessorIdentifier() {
        return PROCESSOR_IDENTIFIER;
    }
    
    private Map<String, String> getParamMap(String fullUrl) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("url",fullUrl);
        return parameters;
    }

}
