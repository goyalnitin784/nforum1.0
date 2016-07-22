package com.nforum.platform.http.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import com.nforum.platform.commons.role.CallContextKeeper;
import com.nforum.platform.commons.utils.RequestUtils;
import com.nforum.platform.commons.utils.ZipUtils;
import com.nforum.platform.exception.ConnectionException;
import com.nforum.platform.exception.DataNotFoundException;
import com.nforum.platform.http.HttpEndPoint;
import com.nforum.platform.http.TransferUtils;
import com.nforum.platform.http.type.HttpMethodType;
import com.nforum.platform.http.type.HttpMethodTypeFactory;
import com.nforum.platform.util.NForumUtil;

public class HttpApacheClientService implements IHttpService{

	private static Logger logger = Logger.getLogger(HttpApacheClientService.class);
	HttpClient httpClient = new HttpClient();

	public HttpApacheClientService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpApacheClientService() {
	}

	@Override
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> additionalHeaders, String postData,
			String postDataType, String postDataCharacterEncoding,boolean isChunkedResponse) {

		String response = null;
		HttpMethodType httpMethodType = null;
		try {
			URL url = new URL(endPoint.getUrl());

			httpMethodType = HttpMethodTypeFactory.getHttpMethodType(
					endPoint.getMethod(),
					endPoint.getUrl(),isChunkedResponse);

			addParameters(parameters, httpMethodType, url);

			httpMethodType.addHeaders(TransferUtils.getTransferHeaders(additionalHeaders));

			addPostData(postData, postDataType, postDataCharacterEncoding, httpMethodType);

			logParameters(httpMethodType);

			response = invoke(httpMethodType);

			logger.trace(response);
			logger.debug("Status - " + httpMethodType.getHttpMethod().getStatusCode());

		} catch (HttpException e) {
			throw new ConnectionException("HttpException : invoke : unable to get response for : "+ endPoint +  e.getReasonCode() + e.getStackTrace(),e);
		} catch (IOException e) {
			throw new ConnectionException("IOException : invoke : unable to get response for : "+ endPoint + e.getStackTrace(), e);
		} catch (Exception e) {
			throw new ConnectionException("Exception : invoke : unable to get response for : "+ endPoint + e.getStackTrace(), e);
		} finally {
			logger.debug("Releasing Connection for REQUEST "+endPoint);
			if (httpMethodType != null
					&& httpMethodType.getHttpMethod() != null)
				httpMethodType.getHttpMethod().releaseConnection();
		}

		if (httpMethodType != null
				&& httpMethodType.getHttpMethod().getStatusCode() >= 400)
			throw new DataNotFoundException("Error in executing http service with error code = "+httpMethodType.getHttpMethod().getStatusCode());

		return response;
	}

	private void logParameters(HttpMethodType httpMethodType) {
		String logg = httpMethodType.getHttpMethod().getPath() + "?"
				+ httpMethodType.getHttpMethod().getQueryString();
		logger.debug(logg);
	}

	private String invoke(HttpMethodType httpMethodType) throws IOException,
			HttpException {

		String response;
		httpMethodType.getHttpMethod().addRequestHeader("Accept-Encoding","gzip");
		httpClient.executeMethod(httpMethodType.getHttpMethod());

		Header encodingHeader = httpMethodType.getHttpMethod().getResponseHeader("Content-Encoding");
		if (encodingHeader != null
				&& "gzip".equalsIgnoreCase(encodingHeader.getValue())) {
			response = ZipUtils.getUnZipped(httpMethodType.getHttpMethod().getResponseBodyAsStream());
		} else {
			response = httpMethodType.getHttpMethod().getResponseBodyAsString();
		}
		
		encodingHeader = httpMethodType.getHttpMethod().getResponseHeader("X-Max-Age");
		if(null != encodingHeader && !NForumUtil.isNullOrEmpty(encodingHeader.getValue())) {
			CallContextKeeper.getCallContext().addContextParam("X-Max-Age", encodingHeader.getValue());
		}
		
		return response;
	}

		private void addPostData(String postData, String postDataType,
			String postDataCharacterEncoding, HttpMethodType httpMethodType)
			throws UnsupportedEncodingException {
		if (postData != null) {
			httpMethodType.addPostData(postDataType,postDataCharacterEncoding,postData);
		}
		}

		private void addParameters(Map<String, String> parameters,
			HttpMethodType httpMethodType, URL url) {
		//Handling query parameters passed as end point url
		Map<String, String> queryParams = RequestUtils.toMap(url.getQuery());
		if(queryParams.size()>0)
		{
			if(parameters==null)
				parameters = new HashMap<String,String>();
			parameters.putAll(queryParams);
		}

		if (parameters != null)
			httpMethodType.setParameters(parameters);
		}

		@Override
		public String invoke(HttpEndPoint endPoint,
				Map<String, String> parameters,
				Map<String, String> additionalHeaders, String postData,
				String postDataType, String postDataCharacterEncoding) {
			return invoke(endPoint, 
				parameters, 
				additionalHeaders,
				postData, 
				postDataType, 
				postDataCharacterEncoding,false);
		}
}
