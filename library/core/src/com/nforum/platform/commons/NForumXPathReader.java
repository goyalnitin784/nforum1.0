package com.nforum.platform.commons;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import com.nforum.platform.commons.utils.XPathReader;

@Service
public class NForumXPathReader {

	public String evaluateXPath(String xmlFile, String expression) {
		XPathReader xPathReader = new XPathReader(new ByteArrayInputStream(xmlFile.getBytes()));
		return  xPathReader.readString(expression);
	}
	
}
