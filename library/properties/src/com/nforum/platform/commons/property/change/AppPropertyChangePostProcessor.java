package com.nforum.platform.commons.property.change;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyChangeListener;

/**
 * Wires listeners to notifiers instead of having this logic in constructors
 * @author goyaln
 *
 */

@Component
public class AppPropertyChangePostProcessor implements BeanPostProcessor {

	Logger logger = Logger.getLogger(AppPropertyChangePostProcessor.class);
	
	@Autowired
	PropertyChangeNotifier notifier;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		
		if(bean instanceof ReloadableProperty)
		{
			logger.info("Registering " + beanName + " with propertyChangeNotifier as ReloadableProperty");
			notifier.addPropertyManager((ReloadableProperty)bean);
		}
		if(bean instanceof PropertyChangeListener)
		{
			logger.info("Registering " + beanName + " with propertyChangeNotifier as PropertyChangeListener");
			notifier.registerForChanges((PropertyChangeListener)bean);
		}
		
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
