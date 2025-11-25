package com.yashir.knowledgehub.user.exception;

/**
 * Exception thrown when attempting to create a user that already exists
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String userName) {
        super("User with name '" + userName + "' already exists");
    }
}

