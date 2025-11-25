package com.yashir.knowledgehub.exception;

/**
 * Base exception for all "Not Found" exceptions
 * Follows DRY principle and provides consistent exception structure
 */
public abstract class BaseNotFoundException extends RuntimeException {
    
    protected BaseNotFoundException(String message) {
        super(message);
    }
    
    protected BaseNotFoundException(String resourceName, Long id) {
        super(String.format("%s not found with id: %d", resourceName, id));
    }
}

