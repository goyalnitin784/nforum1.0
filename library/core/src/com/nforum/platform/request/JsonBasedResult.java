package com.nforum.platform.request;

import java.io.Serializable;

import com.nforum.platform.json.NForumJSON;

public class JsonBasedResult implements Serializable{

	private static final long serialVersionUID = -4643297808405506650L;
	
	protected NForumJSON resultData;
	protected String rawResponse;
		
	public NForumJSON getResultData() {
		return resultData;
	}
	public void setResultData(NForumJSON resultData) {
		this.resultData = resultData;
	}
	public String getRawResponse() {
		return rawResponse;
	}
	public void setRawResponse(String rawResponse) {
		this.rawResponse = rawResponse;
	}
	
}
