package com.nforum.platform.http.type;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpPostMethodType extends HttpMethodTypeBase implements HttpMethodType{

	PostMethod postMethod;

	public HttpPostMethodType(String url,boolean isChunkedResponse) {
		super();
		this.postMethod = new PostMethod(url);
		postMethod.setContentChunked(!isChunkedResponse);
		super.httpMethod = postMethod;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		postMethod.addParameters(getNameValuePairs(parameters));
	}

	@Override
	public HttpMethod getHttpMethod() {
		return postMethod;
	}

	@Override
	public void addPostData(String postData) throws UnsupportedEncodingException {
		postMethod.setRequestEntity(new StringRequestEntity(postData, "text/xml", "ISO-8859-1"));
	}

	@Override
	public void addPostData(String postDataType, String postDataCharacterEncoding,String postData) throws UnsupportedEncodingException {
		if(postDataType==null)
			addPostData(postData);
		else
			postMethod.setRequestEntity(new StringRequestEntity(postData, postDataType, postDataCharacterEncoding));
	}

}
