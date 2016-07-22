package com.nforum.platform.http.service;

import java.util.Map;

import com.nforum.platform.http.HttpEndPoint;

public interface IHttpService {

	public String invoke(
			HttpEndPoint endPoint, 
			Map<String, String> parameters,
			Map<String, String> additionalHeaders,
			String postData, 
			String postDataType,
			String postDataCharacterEncoding,boolean isChunkedResponse );
	
	public String invoke(
			HttpEndPoint endPoint, 
			Map<String, String> parameters,
			Map<String, String> additionalHeaders,
			String postData, 
			String postDataType,
			String postDataCharacterEncoding);
			
			
	
}
