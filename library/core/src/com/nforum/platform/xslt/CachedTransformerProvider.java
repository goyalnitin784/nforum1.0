package com.nforum.platform.xslt;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * caches transformer instances for an xsl file name
 * @author goyaln
 *
 */
@Component("cachedTransformerProvider")
public class CachedTransformerProvider implements IReloadableTransformerProvider {

	private Logger logger = Logger.getLogger(CachedTransformerProvider.class);
	
	@Autowired
	@Qualifier("transformerProvider")
	private ITransformerProvider realTransformerProvider;
	
	/*
	 * Caching transformer so that it does not need xsl files subsequent times
	 * A map will hold a Transformer for a given xsl file
	 * Since a Transformer is not thread safe keeping the map inside a thread local
	 * 
	 * A many maps will be created as there are active threads and as many transformers 
	 * will be created as there are xsl files referred, bu this is much better than disk IO
	 * per transformation
	 * 
	 * The cache will be cleared when properties are changed
	 */
	private ThreadLocal<Map<String,Transformer>> threadTransformerCache = new ThreadLocal<Map<String,Transformer>>(); 
	
	
	@Override
	public Transformer getTransformer(String xslSourceFile) {
		//See if we are yet to create transformer cache for this thread
		Map<String,Transformer> transformerCache = threadTransformerCache.get();
		if(transformerCache == null)
		{
			transformerCache = new HashMap<String,Transformer>();
			threadTransformerCache.set(transformerCache);
		}	
		
		//See we have not yet created a transformer for this xsl file 
		if(!transformerCache.containsKey(xslSourceFile))
		{
			transformerCache.put(xslSourceFile, 
					realTransformerProvider.getTransformer(xslSourceFile));
		}
		
		return transformerCache.get(xslSourceFile);
	}

	@Override
	public void reset() {
		logger.info("Clearing Tranformer cache..");
		threadTransformerCache = new ThreadLocal<Map<String,Transformer>>();
	}

}
