package com.nforum.platform.urlshortener.bitly.responseParser.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.nforum.platform.urlshortener.bitly.responseParser.IBitlyResponseParser;

/**
 * 
 * @author aggarwald
 *
 */
@Service
public class BitlyXmlResponseParser implements IBitlyResponseParser {

    @Override
    public String parse(String response) throws Exception {
        String shortUrl = null;
        if(response != null) {
            // parse the XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader st = new StringReader(response);
            Document d = db.parse(new InputSource(st));
            NodeList nl = d.getElementsByTagName("shortUrl");
            if(nl != null) {
                Node n = nl.item(0);
                shortUrl = n.getTextContent();
            }
        }
 
        return shortUrl;
    }

}
