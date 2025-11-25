package com.yashir.knowledgehub.document.exception;

/**
 * Exception thrown when document processing fails
 * Used for file operations that fail (save, load, etc.)
 */
public class DocumentProcessingException extends RuntimeException {

    public DocumentProcessingException(String message) {
        super(message);
    }

    public DocumentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

