package com.nforum.platform.exception;


public class GetNotSupportedException extends CustomException {

    public GetNotSupportedException(String message) {
        super(message);
    }
    
    public GetNotSupportedException() {
        super();
    }

    /**
     * Generated version UID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 
     * @param message
     * @param cause
     */
    public GetNotSupportedException(String message,Throwable cause) {
        super(message, cause);
    }

}
