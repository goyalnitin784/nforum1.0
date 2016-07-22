package com.nforum.platform.commons.role;

public interface ContextParams {

	String LoginToken = "loginToken";
	String HashedSSOToken = "HashedSsoToken";
	String ClientIpAddress  = "clientIpAddress";
	String HostIpAddress  = "hostIpAddress";
	String Headers = "headersMap";
	String Language = "language";
	String AdditionalCookies = "additionalCookies";  // Cookies that are created in transit, and need to be forwarded to internal requests
	String DefaultLanguage = "en"; // Cookies that are created in transit, and need to be forwarded to internal requests
	String UserAgentInfo = "userAgentInfo";
}
