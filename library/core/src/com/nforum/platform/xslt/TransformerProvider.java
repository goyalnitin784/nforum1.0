package com.nforum.platform.xslt;

import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.nforum.platform.exception.CustomException;
/**
 * Simple implementation of transformer provider that gives a new transformer
 * whenever it is asked
 * @author goyaln
 *
 */
@Component("transformerProvider")
public class TransformerProvider implements ITransformerProvider{

	private TransformerFactory tFactory = TransformerFactory.newInstance();

	@Override
	public Transformer getTransformer(String xslSourceFile) {
	   Transformer transformer=null;
		try {

			ClassPathResource resource = new ClassPathResource(xslSourceFile);
			StreamSource streamSrcFile;
			streamSrcFile = new StreamSource(resource.getFile());
			transformer = tFactory.newTransformer(streamSrcFile);
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");

		} catch(IOException e){
			throw new CustomException("IO Error in applying xsl transform while processing xDist response", e);
		} catch(TransformerConfigurationException e){
			throw new CustomException("Error in loading xsl trasform configuration while processing xDist response", e);
		}
		return transformer;
	}

}
