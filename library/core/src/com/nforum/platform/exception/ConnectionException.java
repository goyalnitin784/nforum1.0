package com.nforum.platform.exception;

public class ConnectionException extends CustomException{

	/**
	 * Generated version UID.
	 */
	private static final long serialVersionUID = -4491026303609452088L;

	

	
	public ConnectionException() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public ConnectionException(String message) {
		super(message);
	}

	/**Code commented. It will use in future
	 * 
	 * @param cause
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public ConnectionException(String message,Throwable cause) {
		super(message, cause);
	}

}
