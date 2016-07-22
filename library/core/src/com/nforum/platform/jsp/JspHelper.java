package com.nforum.platform.jsp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.commons.utils.IO;
import com.nforum.platform.util.NForumUtil;

@Service
public class JspHelper implements PropertyChangeListener {
	
	@Autowired(required=false) private ServletContext context;
	private ConcurrentMap<String, String> templates = new ConcurrentHashMap<String, String>();
	private static final Logger logger = Logger.getLogger(JspHelper.class);
	
	public String getJspTemplate(String templatePath) {
		String templateContent = getTemplateFromCache(templatePath);
		if(NForumUtil.isNullOrEmpty(templateContent)) {
			templateContent = getTemplateFromFileSystem(templatePath);
		}
		
		logger.trace("jsp template path : " + templatePath + ", it's content : " + templateContent);
		return templateContent;
	}

	private String getRealPath(String templatePath) {
		return context.getRealPath("/WEB-INF/" + templatePath);
	}

	private String getTemplateFromCache(String templatePath) {
		return templates.get(templatePath);
	}
	
	private String getTemplateFromFileSystem(String templatePath) {
		String templateContent = IO.readFileContent(getRealPath(templatePath));
		templates.put(templatePath, templateContent);
		return templateContent;
	}

	@Override
	public void propertyChanged() {
		logger.info("Clearing jsp template cache");
		templates = new ConcurrentHashMap<String, String>();
	}

}
