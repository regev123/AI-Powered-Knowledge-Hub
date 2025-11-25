package com.yashir.knowledgehub.security.exception;

import com.yashir.knowledgehub.exception.ErrorResponse;
import com.yashir.knowledgehub.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for Security domain exceptions
 * Handles only security-related exceptions following domain-driven design principles
 * Extends GlobalExceptionHandler to reuse buildErrorResponse method
 */
@RestControllerAdvice
public class SecurityExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.FORBIDDEN,
            "Access Denied",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}

