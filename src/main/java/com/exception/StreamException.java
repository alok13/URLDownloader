package com.exception;

/**
 * This Stream exception wraps all checked Stream and IO expection and enhances with custom error messages
 */

public class StreamException extends Exception {
    public StreamException(String message) {
        super(message);
    }

    public StreamException(String message,Throwable cause) {
        super(message,cause);
    }

}
