package com.nforum.platform.http.service;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.nforum.platform.http.HttpEndPoint;

public class DefaultApacheHttpService implements IHttpService {

	@Override
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> additionalHeaders, String postData,
			String postDataType, String postDataCharacterEncoding,boolean isChunkedResponse) {
		
		HttpClient httpClient = new HttpClient();
		//TODO: Set properties
		
		return new HttpApacheClientService(httpClient).invoke(
				endPoint, 
				parameters, 
				additionalHeaders, 
				postData, 
				postDataType, 
				postDataCharacterEncoding,
			    isChunkedResponse);
	}

	@Override
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> additionalHeaders, String postData,
			String postDataType, String postDataCharacterEncoding) {
		return invoke(endPoint, 
				parameters, 
				additionalHeaders, 
				postData, 
				postDataType, 
				postDataCharacterEncoding,
			    false);
	}
}
