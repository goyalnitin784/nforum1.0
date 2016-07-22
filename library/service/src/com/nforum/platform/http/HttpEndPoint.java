package com.nforum.platform.http;

import com.nforum.platform.util.NForumUtil;

public class HttpEndPoint {

	String url;
	String method;

	public HttpEndPoint(String url, String method) {
		super();
		this.url = url;
		this.method = method;
		if(NForumUtil.isNullOrEmpty(url) || NForumUtil.isNullOrEmpty(method))
			throw new RuntimeException("Unsupportted method type/ url ");
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("url=").append(url).append(";");
		builder.append("method=").append(method);
		return builder.toString();
	}

   public void setUrl(String url){
	   this.url = url;
   }

   public void setMethod(String method){
	   this.method = method;
   }

}
