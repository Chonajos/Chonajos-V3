package com.web.chon.core.exception;

public class SecurityAccessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message = null;

    //Parameterless Constructor
    public SecurityAccessException() {
        super();
    }

    //Constructor that accepts a message
    public SecurityAccessException(String message) {
        super(message);
        this.message = message;
    }

    //Constructor that accepts a Throwable cause
    public SecurityAccessException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
