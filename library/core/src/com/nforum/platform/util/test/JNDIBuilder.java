package com.nforum.platform.util.test;

import java.io.IOException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.w3c.dom.NodeList;

import com.nforum.platform.commons.utils.XPathReader;

//@Component
public class JNDIBuilder implements ApplicationContextAware{

	Logger logger = Logger.getLogger(JNDIBuilder.class);
	
	@Override
	public void setApplicationContext(ApplicationContext context) 
	{
        SimpleNamingContextBuilder builder;
        XPathReader xPathReader;
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
			xPathReader = new XPathReader(
					new ClassPathResource("META-INF/persistence.xml").getInputStream());

		} catch (NamingException e) {
			return;
		} catch (IOException e) {
			return;
		}
        
        NodeList dsNameList = xPathReader.readNodeList("persistence/persistence-unit/@name");
        NodeList jndiNameList = xPathReader.readNodeList("persistence/persistence-unit/non-jta-data-source");
        
        for(int i=0;i<dsNameList.getLength();i++)
        {
        	String dsName = dsNameList.item(i).getNodeValue();
        	DataSource dataSource = (DataSource)context.getBean(dsName);
        	String jndiName = jndiNameList.item(i).getFirstChild().getNodeValue();
        	logger.info("Binding" + dsName + " to JNDI name " + jndiName);
        	builder.bind(jndiName,dataSource);
        }
	}
}
