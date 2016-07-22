package com.nforum.platform.http.type;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.nforum.platform.exception.ConnectionException;

public class HttpGetMethodType extends HttpMethodTypeBase implements HttpMethodType {

	GetMethod getMethod;
	private Map<String,String> paramsMap = new HashMap<String,String>();
	private static Logger logger = Logger.getLogger(HttpGetMethodType.class);

	public HttpGetMethodType(String url)
	{
		getMethod = new GetMethod(url);
		super.httpMethod = getMethod;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		getMethod.setQueryString(getNameValuePairs(parameters));
	}

	@Override
	public HttpMethod getHttpMethod() {
		return getMethod;
	}

	public void addParameter(String key,String value)
	{
		paramsMap.put(key, value);
		setParameters(paramsMap);

	}

	public String getCompleteUrl()
	{
		try {
			return getMethod.getURI().toString();
		} catch (URIException e) {
			logger.error(e);
		}
		return "";
	}

	@Override
	public void addPostData(String postData) {
		throw new ConnectionException("Post Data passed to a HTTP GET mthod");
	}

	@Override
	public void addPostData(String postDataType,
			String postDataCharacterEncoding, String postData)
			throws UnsupportedEncodingException {
		throw new ConnectionException("Post Data passed to a HTTP GET mthod");

	}



}
