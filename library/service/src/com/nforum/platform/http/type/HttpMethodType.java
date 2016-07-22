package com.nforum.platform.http.type;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;

public interface HttpMethodType {

	void setParameters(Map<String,String> parameters);
	HttpMethod getHttpMethod();
	void addPostData(String postData) throws UnsupportedEncodingException;
	void addHeaders(Map<String, String> headers);
	void addPostData(String postDataType, String postDataCharacterEncoding,	String postData) throws UnsupportedEncodingException;
}
