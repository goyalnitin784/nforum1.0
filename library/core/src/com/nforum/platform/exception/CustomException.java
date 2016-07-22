package com.nforum.platform.exception;

public class CustomException extends RuntimeException {

	/**
	 * Generated version UID.
	 */
	private static final long serialVersionUID = 1L;

	
	public CustomException() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public CustomException(String message) {
		super(message);
	}

	/**Code commented. It will use in future
	 * 
	 * @param cause
	 */
	public CustomException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public CustomException(String message,Throwable cause) {
		super(message, cause);
	}

}
