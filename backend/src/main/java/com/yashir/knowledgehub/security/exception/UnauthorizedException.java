package com.yashir.knowledgehub.security.exception;

/**
 * Exception thrown when a user does not have the required role to access a resource
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

