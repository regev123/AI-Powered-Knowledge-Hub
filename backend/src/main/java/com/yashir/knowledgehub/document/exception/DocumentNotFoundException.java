package com.yashir.knowledgehub.document.exception;

import com.yashir.knowledgehub.exception.BaseNotFoundException;

/**
 * Exception thrown when a document is not found
 * Extends BaseNotFoundException following inheritance and DRY principles
 */
public class DocumentNotFoundException extends BaseNotFoundException {
    
    private static final String RESOURCE_NAME = "Document";
    
    public DocumentNotFoundException(Long id) {
        super(RESOURCE_NAME, id);
    }
}

