package com.yashir.knowledgehub.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Global exception handler for the application
 * Handles all exception mapping to HTTP responses
 * Follows Open/Closed Principle - can be extended without modification
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseNotFoundException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.NOT_FOUND,
            "Resource Not Found",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Validation Failed",
            message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Validation Failed",
            message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Invalid Argument",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String message = ex.getMessage() != null 
            ? ex.getMessage() 
            : "An unexpected error occurred";
        
        ErrorResponse error = buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            message
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds an ErrorResponse object
     * @param status the HTTP status
     * @param error the error type
     * @param message the error message
     * @return the ErrorResponse object
     */
    protected ErrorResponse buildErrorResponse(HttpStatus status, String error, String message) {
        return new ErrorResponse(status.value(), error, message);
    }
}

