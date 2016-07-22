package com.nforum.platform.commons.message.source;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service("nforumMessageSource")
public class NForumMessageSource {
	ReloadableResourceBundleMessageSource springMessageSource;

	public NForumMessageSource() {
	    super();
	}
	
	public NForumMessageSource(
			ReloadableResourceBundleMessageSource springMessageSource) {
		super();
		this.springMessageSource = springMessageSource;
	}

	public String getMessage(String key)
	{
		return springMessageSource.getMessage(key, new Object[]{}, Locale.getDefault());
	}

	public String getMessage(String key, Object[] args)
	{
		return springMessageSource.getMessage(key, args, Locale.getDefault());
	}

	public String getMessage(String key, Object[] args, Locale locale)
	{
		return springMessageSource.getMessage(key, args, locale);
	}
}
