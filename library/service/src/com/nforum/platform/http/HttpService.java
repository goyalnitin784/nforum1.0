package com.nforum.platform.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.nforum.platform.http.service.HttpApacheClientService;
import com.nforum.platform.http.service.IHttpService;


/**
 * Class that uses apache's HttpClient API for http requests 
 * @author goyaln
 *
 */
public class HttpService {

	protected IHttpService httpServieStub;
	
	public HttpService() {
		this.httpServieStub = new HttpApacheClientService();  
	}

	public HttpService(HttpClient httpClient) {
		this.httpServieStub = new HttpApacheClientService(httpClient);  
	}
	
	public HttpService(IHttpService httpServieStub) {
		this.httpServieStub = httpServieStub;  
	}
	
	public String invoke(String endPointUrl,Map<String, String> parameters) {
		HttpEndPoint endPoint = new HttpEndPoint(endPointUrl, "GET");
		return invoke(endPoint,parameters);
	}

	public String invoke(String endPointUrl) {
		HttpEndPoint endPoint = new HttpEndPoint(endPointUrl, "GET");
		return invoke(endPoint);
	}

	public String invoke(HttpEndPoint endPoint) {
		return this.invoke(endPoint, null);
	}

	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters) {
		return invoke(endPoint, parameters, null, null,false);
	}
	
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters, Map<String, String> headers) {
		return invoke(endPoint, parameters, headers, null,false);
	}

	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> headers, String postData,boolean isChunkedResponse) {
		return invoke(endPoint, parameters,headers, postData,null,null,isChunkedResponse);
	}
	
	
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> headers, String postData) {
		return invoke(endPoint, parameters,headers, postData,null,null,false);
	}

	public String invoke(
			HttpEndPoint endPoint, 
			Map<String, String> parameters,
			Map<String, String> additionalHeaders,
			String postData, 
			String postDataType,
			String postDataCharacterEncoding,boolean isChunkedResponse ) 
	{
		return httpServieStub.invoke(
				endPoint, 
				parameters, 
				additionalHeaders,
				postData, 
				postDataType, 
				postDataCharacterEncoding,isChunkedResponse);
	}






}
