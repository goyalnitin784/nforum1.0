package com.nforum.platform.util.convert;

import org.springframework.stereotype.Service;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Service
public class XmlToJson implements Converter<String, JSON> {

	@Override
	public JSON convert(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml);
	}
}
