package com.exception;


/**
 * This ValidationException validate input as per requirement and throws custom message upon failure
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
