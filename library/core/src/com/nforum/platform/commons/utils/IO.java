package com.nforum.platform.commons.utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.nforum.platform.exception.CustomException;

public class IO {
    
    private static final Logger logger = Logger.getLogger(IO.class);

	public static String readFileContent(String fileName) {
		InputStream in = getInputStream(fileName);
		String ret = readStreamContent(in);
		try{in.close();}catch(Exception e){}
		return ret;
	}
	
	public static String readStreamContent(InputStream in)
	{
		 try {
			return IOUtils.toString(in,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return "";
	}

	public static String readUrlContent(String url)
	{
		try {
			return readStreamContent(new URL(url).openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Caller should take care of closing the stream
	 */
	public static InputStream getInputStream(String fileName)
	{
		try {
			return new FileInputStream(new ClassPathResource(fileName).getFile().getAbsoluteFile());
		} catch (FileNotFoundException e) {
			try{
				
				InputStream in = new ClassPathResource(fileName).getInputStream();
				if(in==null)
					throw new IOException();
				return in;
			} catch (IOException e1) {
				try {
					return new FileInputStream(fileName);
				} catch (FileNotFoundException e2) {
					throw new CustomException("File not found :: " + fileName, e);
				}
			}
		} catch (IOException e) {
			throw new CustomException("Exception while reading file :: " + fileName, e);
		}
	}
	
	public static void writeToFile(String fileName, String content){
		if(content==null)
			content="";
		
		BufferedOutputStream out = null;
		OutputStreamWriter streamWriter = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(fileName));
			streamWriter = new OutputStreamWriter(out,"UTF-8");
			streamWriter.write(content);
			streamWriter.flush();
		} catch (IOException e) {
			 logger.error(e.getMessage());
		}
		finally {
			try{
				if(out!=null){
					out.close();
				}
				if(streamWriter!=null){
					streamWriter.close();
				}
			}catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

	}
	
	public static void dump(InputStream in, OutputStream out)
	{
		byte buffer[] = new byte[1];
		int len;
		try {
			while( (len=in.read(buffer)) !=-1)
				out.write(buffer, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try
		{
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void pdfDump(InputStream in, OutputStream out)
	{
		byte buffer[] = new byte[2048];
		try {
			int len;
			while ((len = in.read(buffer)) > 0)
				out.write(buffer, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
