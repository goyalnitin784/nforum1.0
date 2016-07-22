package com.nforum.platform.commons;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.log.SystemLogChute;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl;
import org.apache.velocity.tools.view.WebappResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.exception.CustomException;

@Service
public class VelocityHelper implements PropertyChangeListener{

	@Autowired(required=false) VelocityEngine ve;
	@Autowired(required=false) ServletContext servletContext;

	ThreadLocal<Map<String,Template>> templates = new ThreadLocal<Map<String,Template>>();
	static final Logger logger = Logger.getLogger(VelocityHelper.class);
	Object templateMutex = new Object();

	@Override
	public void propertyChanged() {
		logger.info("Clearing search request velocity template cache");
		templates = new ThreadLocal<Map<String,Template> >();
		//ve.setProperty("class.resource.loader.modificationCheckInterval", "1");
	}


	public Template getTemplate(String templatePath) {

		try {
			Map<String, Template> templateMap = templates.get();

			if (templateMap == null)
			{
				templateMap = new HashMap<String, Template>();
				templates.set(templateMap);
			}
			Template template = templateMap.get(templatePath);

			if (template == null) {
				synchronized (templateMutex) {
					if (template == null) {
						template = getVelocityEngine()
								.getTemplate(templatePath);
						templateMap.put(templatePath, template);
					}
				}
			}
			return template;

		} catch (ResourceNotFoundException e) {
			throw new CustomException("No template file found", e);
		} catch (ParseErrorException e) {
			throw new CustomException("Error parsing velocity template", e);

		} catch (Exception e) {
			throw new CustomException("Error Initializing velocity engine", e);
		}

	}


	public String velocityConvertor(Template template, Map<String,Object> requestMap){

		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		context.put("requestMap", requestMap);
//		try
//		{
			template.merge(context, writer);
//		} 
//		catch (IOException e) {
//			throw new CustomException("Velocity converter exception", e);
//		}

		String requestXML = writer.toString();
		writer.getBuffer().setLength(0);

		return requestXML;
	}


	public String velocityConvertor(String templatePath, Map<String,Object> requestMap){
		return velocityConvertor(getTemplate(templatePath), requestMap);
	}


	protected VelocityEngine newStringEngine(String repoName, boolean isStatic)
	{
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(Velocity.RESOURCE_LOADER, "string");
		engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());

		if (repoName != null)
		{
			engine.addProperty("string.resource.loader.repository.name", repoName);
		}

		if (!isStatic)
		{
			engine.addProperty("string.resource.loader.repository.static", "false");
		}
		engine.addProperty("string.resource.loader.modificationCheckInterval", "1");
		engine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, SystemLogChute.class.getName());

		return engine;

	}


	public String velocityConvertorFromString(String template, Map<String,Object> requestMap) throws Exception
	{
		VelocityEngine engine = newStringEngine("my.app.repo", false);
		MyRepo repo = new MyRepo();
		repo.put("temparyTemplate.vm", template);
		engine.setApplicationAttribute("my.app.repo", repo);
		return velocityConvertor(engine.getTemplate("temparyTemplate.vm"),requestMap);
	}


	public static class MyRepo extends StringResourceRepositoryImpl
	{
		public void put(String name, String template)
		{

			putStringResource(name, template);

		}
	}


	private VelocityEngine getVelocityEngine() {
		if(ve==null)
		{
			ve = new VelocityEngine();
			ve.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
			ve.addProperty("resource.loader","webapp");
			ve.addProperty("webapp.resource.loader.class",WebappResourceLoader.class.getName());
			ve.addProperty("webapp.resource.loader.path","/WEB-INF/classes/");
			ve.addProperty("resourceLoaderPath","/WEB-INF/classes/");
			ve.addProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.NullLogSystem");
			ve.addProperty("webapp.resource.loader.cache",false);
		}
		return ve;
	}
}
