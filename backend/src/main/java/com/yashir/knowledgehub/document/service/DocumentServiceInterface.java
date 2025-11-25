package com.yashir.knowledgehub.document.service;

import com.yashir.knowledgehub.document.dto.DocumentResponse;
import com.yashir.knowledgehub.document.dto.DocumentUploadRequest;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Service interface for Document operations
 */
public interface DocumentServiceInterface {
    List<DocumentResponse> getAllDocuments();
    DocumentResponse uploadDocument(DocumentUploadRequest request, String uploadedBy);
    Resource downloadDocument(Long documentId);
    DocumentResponse getDocumentById(Long documentId);
    void deleteDocument(Long documentId);
}

