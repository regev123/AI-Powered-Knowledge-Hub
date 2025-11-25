package com.yashir.knowledgehub.document.exception;

/**
 * Exception thrown when attempting to upload a document that already exists
 */
public class DocumentAlreadyExistsException extends RuntimeException {
    
    public DocumentAlreadyExistsException(String fileName) {
        super("Document with file name '" + fileName + "' already exists");
    }
}

