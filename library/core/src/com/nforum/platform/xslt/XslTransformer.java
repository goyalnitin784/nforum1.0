package com.nforum.platform.xslt;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.utils.IO;
import com.nforum.platform.commons.utils.Timeit;
import com.nforum.platform.exception.CustomException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Applies xsl transform given a xsl, xml filename and transformer parameters
 * caches transformers by xsl file name
 * @author goyaln
 *
 */
@Component
public class XslTransformer {

	private Logger logger = Logger.getLogger(XslTransformer.class);
	
	@Autowired 
	private XslTransformerFactory taransformerProviderFactory;
	
	public String applyXslTransformToFile(
			String xslSourceFile, 
			String xmlSourceFileName, 
			Map<String,Object> transformerParameters)
	{
		return this.applyXslTransform(xslSourceFile, 
				IO.readFileContent(xmlSourceFileName), 
				transformerParameters);
	}
	
	public String applyXslTransform(
			String xslSourceFile, 
			String xmlSource, 
			Map<String,Object> transformerParameters){
		
		logger.debug("Applying " + xslSourceFile );
		
		try {
			
			Transformer transformer = taransformerProviderFactory.getTransformerProvider().getTransformer(xslSourceFile);
			
			appendParametersIfAny(transformer,transformerParameters);
			Writer paramWriter = new StringWriter();
			
			Timeit.timeIt("Actual xslt time");
			transformer.transform(
					new StreamSource(new StringReader(xmlSource)), 
					new StreamResult(paramWriter));
			Timeit.timeUp();
			
			transformer.clearParameters();
			return paramWriter.toString().trim();
	    
		} catch(TransformerException e){
			throw new CustomException("Error in applying xsl transform while processing xDist response", e);
		}catch(Exception e){
    		throw new CustomException("Error while processing xDist response", e);
		}	    
	}

	private void appendParametersIfAny(Transformer transformer, 
			Map<String, Object> transformerParameters){		
		if(transformerParameters != null){
			for(Map.Entry<String,Object> e : transformerParameters.entrySet())
				transformer.setParameter(e.getKey(),e.getValue());
		}
	}

	public JSONObject applyXslToJsonTransform(String xslSourceFile, String xmlSource) 
	{
		return applyXslToJsonTransform(xslSourceFile, xmlSource, null);
	}

	public JSONObject applyXslToJsonTransform(String xslSourceFile,
			String xmlSource, Map<String, Object> transformerParameters) {
	    JSONObject jsonObject = null;
		String transformStr = applyXslTransform(xslSourceFile, xmlSource, transformerParameters);
		if (transformStr != null) {
			transformStr = transformStr.trim();
		}
		try{
		    jsonObject = (JSONObject) JSONSerializer.toJSON(transformStr);
		}catch(JSONException e) {
		    logger.error(e);
		    jsonObject = null;
		}
		
		return jsonObject;
	}
	
	public String applyStandAlone(String xsl,String xml, Map<String,Object> transformerMap)
	{
		Transformer transformer =new TransformerProvider().getTransformer(xsl);
		Writer paramWriter = new StringWriter();
		if(transformerMap != null){
			for(Map.Entry<String,Object> e : transformerMap.entrySet())
				transformer.setParameter(e.getKey(),e.getValue());
		}

		try {
			transformer.transform(
					new StreamSource(new StringReader(xml)), 
					new StreamResult(paramWriter));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String jsonStr = paramWriter.toString().trim();

		return paramWriter.toString().trim();
	}
}
