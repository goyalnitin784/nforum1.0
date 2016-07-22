package com.nforum.platform.exception;

public class ValidationException extends CustomException {

    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException() {
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
    public ValidationException(String message,Throwable cause) {
        super(message, cause);
    }

}
