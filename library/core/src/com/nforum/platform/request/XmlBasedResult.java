package com.nforum.platform.request;

import java.io.Serializable;

public class XmlBasedResult implements Serializable{

	private static final long serialVersionUID = -5901284062763306879L;

	String rawXml;

	public XmlBasedResult(String rawXml) {
		super();
		this.rawXml = rawXml;
	}

	public String getRawXml() {
		return rawXml;
	}

	public void setRawXml(String xml){
		this.rawXml = xml;
	}

}
