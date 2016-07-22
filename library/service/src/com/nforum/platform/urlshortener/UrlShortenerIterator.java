package com.nforum.platform.urlshortener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class iterate over a list of url processor implementations till it get short url.
 * @author aggarwald
 *
 */
@Service
public class UrlShortenerIterator {
    
    private static Logger logger = Logger.getLogger(UrlShortenerIterator.class);
    
    private List<IUrlProcessor> processors;
    
    public String iterate(String fullUrl) {
        logger.debug("Entering into UrlShortenerIterator.iterate with fullUrl[" + fullUrl + "]" );
        String shortUrl = null;
        if (fullUrl != null) {
            try {
                if (processors != null && processors.size() > 0) {
                    for (IUrlProcessor processor : processors) {
                        shortUrl = processor.shortenUrl(fullUrl);
                        if (shortUrl != null) {
                            logger.debug("ShortUrl created via processor[" + processor.getProcessorIdentifier() + "]" );
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error in UrlShortenerIterator", e);
            }
        }
        logger.debug("Exiting from UrlShortenerIterator.iterate with shortUrl[" + shortUrl + "]" );
        return shortUrl;
    }

    public List<IUrlProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<IUrlProcessor> processors) {
        this.processors = processors;
    }
    
    

}
