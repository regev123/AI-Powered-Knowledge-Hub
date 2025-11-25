package com.yashir.knowledgehub.user.exception;

import com.yashir.knowledgehub.exception.ErrorResponse;
import com.yashir.knowledgehub.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for User domain exceptions
 * Handles only user-related exceptions following domain-driven design principles
 * Extends GlobalExceptionHandler to reuse buildErrorResponse method
 */
@RestControllerAdvice
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.CONFLICT,
            "User Already Exists",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}

