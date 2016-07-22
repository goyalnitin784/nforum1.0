package com.nforum.platform.http.service;

import com.nforum.platform.http.HttpService;

public class SimpleSocketServiceContainer extends HttpService {

	public SimpleSocketServiceContainer(IHttpService httpServieStub) {
		super.httpServieStub = httpServieStub;  
	}
}
