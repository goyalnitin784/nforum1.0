package com.nforum.platform.http;

import java.util.ArrayList;
import java.util.List;

public class SoapEnvelop {

	String soapMessage;
	
	private SoapEnvelop(String soapMessage){
		this.soapMessage = soapMessage;
	}
	
	public static SoapEnvelop fromPayload(String payload)
	{
		return new SoapEnvelop(constructMessage(payload));
	}
	
	public static SoapEnvelop fromPayload(String payload,String[] extraXmlns)
	{
		return new SoapEnvelop(constructMessage(payload,extraXmlns));
	}
	
	public static String constructMessage(String payload, String[] extraXmlns) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<soapenv:Envelope xmlns:cli=\"http://client.openjaw.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "+getExtraXmlns(extraXmlns)+">\n");
		sb.append("<soapenv:Body>\n");
		payload=replaceNameSpace(payload);
		sb.append(payload);
		//sb.append(payload);
		sb.append("</soapenv:Body>\n");
		sb.append("</soapenv:Envelope>");
		return sb.toString();
	}

	private static String replaceNameSpace(String payload) {
		List<String> nameSpaces=new ArrayList<String>();
		nameSpaces.add("ns1:");
		nameSpaces.add(":ns1");
		nameSpaces.add("ns2:");
		nameSpaces.add(":ns2");
		
	
		for(String nameSpace : nameSpaces){
			payload=payload.replaceAll(nameSpace, "");
		}
		
		return  payload;
	}

	private static String getExtraXmlns(String[] extraXmlns) {
		
		String extraXmlnsStr="";
		for(int i=0;i<extraXmlns.length;i++){
			extraXmlnsStr=extraXmlnsStr+" xmlns="+extraXmlns[i];
		}
		return extraXmlnsStr;
	}

	public static SoapEnvelop fromMessage(String soapMessage)
	{
		return new SoapEnvelop(soapMessage);
	}
	
	private static String constructMessage(String payload)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<soapenv:Envelope xmlns:cli=\"http://client.openjaw.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\n");
		sb.append("<soapenv:Body>\n");
		sb.append(payload.replaceAll("ns2:", "").replaceAll(":ns2", ""));
		//sb.append(payload);
		sb.append("</soapenv:Body>\n");
		sb.append("</soapenv:Envelope>");
		return sb.toString();

	}
	
	@Override
	public String toString()
	{
		return soapMessage;
	}
	
	public String getBody()
	{
		return 
		soapMessage.substring(
				soapMessage.indexOf("<soapenv:Body>")+ "<soapenv:Body>".length(),
				soapMessage.lastIndexOf("</soapenv:Body>"));
	}
	
}
