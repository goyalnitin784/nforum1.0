package com.nforum.platform.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.nforum.platform.exception.CustomException;

public class NForumHttpUtils {
	
	public static String getURLResponse(
    		String postData,
    		String urlStr,
    		int timeOut) 
    throws IOException
    {
    	return getURLResponse(postData,urlStr,timeOut,"");
    }
    
    public static String getURLResponse(
    		String postData,
    		String urlStr,
    		int timeOut,
    		String myCookie) 
    throws IOException
    {
		if(urlStr==null){
			throw new CustomException("invalid url");
		}
    	OutputStreamWriter writer = null;
		InputStream inputStream = null;
		OutputStream out = null;
		String offerReceived = null;
		URL url = null;
		URLConnection conn=null;
		try{
			url = new URL(urlStr);
			conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(timeOut);
			conn.setRequestProperty("Cookie", myCookie);

			((HttpURLConnection) conn).setRequestMethod("POST");
			writer = new OutputStreamWriter(conn.getOutputStream());

			writer.write(postData);
			writer.flush();

			inputStream = conn.getInputStream();
			out = new ByteArrayOutputStream();
			final byte[] buffer = new byte[1024];
			while (true) {
				final int readCount = inputStream.read(buffer);
				if (readCount == -1)
					break;
				out.write(buffer, 0, readCount);
			}

			offerReceived = out.toString();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}if(inputStream!=null){
				inputStream.close();
			}if(writer!=null){
				writer.close();
			}
		}
		
		
		return  offerReceived;
	}
}
