package com.nforum.platform.xslt;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.commons.property.PropertyManager;

/**
 * sets correct transformer provider to xsl transformer based on property configuration
 * It can be a cache based or a non cache based
 * Sets transformer provider again if properties are changed 
 * @author goyaln
 *
 */
@Component
public class XslTransformerFactory implements PropertyChangeListener{

	private Logger logger = Logger.getLogger(XslTransformerFactory.class);
	
	//Would refer to one of the transformer providers at a time
	private ITransformerProvider currentTransformerProvider;
	
	@Autowired 
	@Qualifier("transformerProvider")
	private ITransformerProvider transformerProvider;
	
	@Autowired 
	@Qualifier("cachedTransformerProvider")
	private IReloadableTransformerProvider cachedTransformerProvider;
	
	@Autowired PropertyManager properties;
	
	@PostConstruct
	public void configureXslTransformer()
	{
		if(!properties.getPropertyAsBoolean("cache.xsl.transformer.instance"))
		{
			logger.info("Configuring non cached transformer provider to xslTransformer");
			currentTransformerProvider = transformerProvider;
		}
		else
		{
			logger.info("Configuring cached transformer provider to xslTransformer");
			currentTransformerProvider = cachedTransformerProvider;
		}
	}

	public ITransformerProvider getTransformerProvider()
	{
		return currentTransformerProvider;
	}
	 
	@Override
	public void propertyChanged() 
	{
		logger.info("Property reload notification received");
		cachedTransformerProvider.reset();
		configureXslTransformer();
	}
}
