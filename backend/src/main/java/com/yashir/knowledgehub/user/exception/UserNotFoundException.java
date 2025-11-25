package com.yashir.knowledgehub.user.exception;

import com.yashir.knowledgehub.exception.BaseNotFoundException;

/**
 * Exception thrown when a user is not found
 * Extends BaseNotFoundException following inheritance and DRY principles
 */
public class UserNotFoundException extends BaseNotFoundException {
    
    private static final String RESOURCE_NAME = "User";
    
    public UserNotFoundException(Long id) {
        super(RESOURCE_NAME, id);
    }
}

