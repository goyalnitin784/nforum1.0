package com.nforum.platform.http.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nforum.platform.exception.ConnectionException;
import com.nforum.platform.exception.DataNotFoundException;
import com.nforum.platform.http.HttpEndPoint;
import com.nforum.platform.http.TransferUtils;
import com.nforum.platform.util.UrlConstructorSimple;
import com.nforum.platform.util.NForumUtil;

/**
 * Class that uses java's URL API for http communication
 *
 * @author goyaln
 *
 */

//TODO: Add zip support
//TODO: Buffered write post data

@Service
public class HttpSocketService implements IHttpService{

	private static Logger logger = Logger.getLogger(HttpApacheClientService.class);

	@Override
	public String invoke(
			HttpEndPoint endPoint,
			Map<String, String> parameters,
			Map<String, String> additionalHeaders,
			String postData,
			String postDataType,
			String postDataCharacterEncoding,boolean chunkedResponse )
	{
		HttpURLConnection connection = null;
		try {

			//If it is a GET request parameters are to be added to URL itself
			UrlConstructorSimple urlConstructor = new UrlConstructorSimple();

			String finalUrl;
			if("GET".equals(endPoint.getMethod()))
				finalUrl = urlConstructor.construct(endPoint.getUrl(),parameters);
			else
				finalUrl = endPoint.getUrl();

			logger.debug(finalUrl);

			URL url = new URL(finalUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(endPoint.getMethod());

			//Add Headers
			for(Entry<String,String> entry:TransferUtils.getTransferHeaders(additionalHeaders).entrySet())
				connection.addRequestProperty(entry.getKey(),entry.getValue());
			
		    connection.setUseCaches (false);
		    connection.setDoInput(true);

		    if("POST".equals(endPoint.getMethod()))
		    {
		    	connection.setDoOutput(true);

		    	String payloadData;
		        if(postData!=null)
		        {
		        	if(!NForumUtil.isNullOrEmpty(postDataType) && !NForumUtil.isNullOrEmpty(postDataCharacterEncoding))
		        	{
		        		connection.addRequestProperty("Content-type",postDataType);
		        		connection.addRequestProperty("Content-Encoding",postDataCharacterEncoding);
		        	}
		        	else
		        	{
		        		connection.addRequestProperty("Content-type","text/xml");
		        		connection.addRequestProperty("Content-Encoding","ISO-8859-1");
		        	}
		        	payloadData= postData;
		        }
		        else
		        {
		        	String parameterContent = urlConstructor.construct("", parameters);
		        	payloadData=parameterContent;
		        	
		        }
		        
	        	connection.setRequestProperty("Content-Length", "" + payloadData.getBytes().length);
	        	DataOutputStream dos = new DataOutputStream (connection.getOutputStream ());
	        	dos.writeBytes(payloadData);
	        	dos.flush();
	        	dos.close();

		    }

		    InputStream is  = connection.getInputStream();

		    int responseCode = connection.getResponseCode();
		    logger.debug(responseCode);

			if (responseCode >= 400)
				throw new DataNotFoundException("Error in executing http service with error code = "+responseCode);

		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer();
		    while((line = rd.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }

		    rd.close();
		    return response.toString();

		} catch (HttpException e) {
			throw new ConnectionException("HttpException : invoke : unable to get response for : "+ endPoint +  e.getReasonCode() + e.getStackTrace(),e);
		} catch (IOException e) {
			throw new ConnectionException("IOException : invoke : unable to get response for : "+ endPoint + e.getStackTrace(), e);
		} catch (Exception e) {
			throw new ConnectionException("Exception : invoke : unable to get response for : "+ endPoint + e.getStackTrace(), e);
		} finally {
			try{
			if(connection!=null)
				connection.disconnect();
			}catch(Exception e){}
		}
	}

	@Override
	public String invoke(HttpEndPoint endPoint, Map<String, String> parameters,
			Map<String, String> additionalHeaders, String postData,
			String postDataType, String postDataCharacterEncoding) {
		return invoke(endPoint, parameters, additionalHeaders, postData, postDataType, postDataCharacterEncoding, false);
	}

}
