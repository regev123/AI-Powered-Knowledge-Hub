package com.yashir.knowledgehub.document.exception;

import com.yashir.knowledgehub.exception.ErrorResponse;
import com.yashir.knowledgehub.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for Document domain exceptions
 * Handles only document-related exceptions following domain-driven design principles
 * Extends GlobalExceptionHandler to reuse buildErrorResponse method
 */
@RestControllerAdvice
public class DocumentExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(DocumentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDocumentAlreadyExistsException(DocumentAlreadyExistsException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.CONFLICT,
            "Document Already Exists",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DocumentProcessingException.class)
    public ResponseEntity<ErrorResponse> handleDocumentProcessingException(DocumentProcessingException ex) {
        ErrorResponse error = buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Document Processing Error",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

