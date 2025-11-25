package com.yashir.knowledgehub.document.controller;

import com.yashir.knowledgehub.document.dto.DocumentResponse;
import com.yashir.knowledgehub.document.dto.DocumentUploadRequest;
import com.yashir.knowledgehub.document.dto.QuestionRequest;
import com.yashir.knowledgehub.document.dto.QuestionResponse;
import com.yashir.knowledgehub.document.service.ContentTypeService;
import com.yashir.knowledgehub.document.service.DocumentQAService;
import com.yashir.knowledgehub.document.service.DocumentServiceInterface;
import com.yashir.knowledgehub.security.annotation.RequireRole;
import com.yashir.knowledgehub.user.model.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Document operations
 * Handles only HTTP concerns and delegates business logic to service layer
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceInterface documentService;
    private final ContentTypeService contentTypeService;
    private final DocumentQAService documentQAService;

    /**
     * Retrieves all documents
     * Requires ADMIN role
     * @return ResponseEntity containing list of all documents
     */
    @GetMapping
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        List<DocumentResponse> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    /**
     * Uploads a new document
     * Requires ADMIN role
     * @param request the document upload request with file and validation
     * @param uploadedBy the name of the user uploading the document (from header)
     * @return ResponseEntity containing the uploaded document with HTTP 201 status
     */
    @PostMapping("/upload")
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<DocumentResponse> uploadDocument(
            @Valid @ModelAttribute DocumentUploadRequest request,
            @RequestHeader(value = "X-User-Name", required = false) String uploadedBy) {
        // Default to "Unknown User" if header is not provided
        String uploaderName = uploadedBy != null ? uploadedBy : "Unknown User";
        DocumentResponse response = documentService.uploadDocument(request, uploaderName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Views a document file in the browser
     * @param documentId the ID of the document to view
     * @return ResponseEntity containing the file resource for viewing
     */
    @GetMapping("/{documentId}/view")
    public ResponseEntity<Resource> viewDocument(@PathVariable Long documentId) {
        // Get document metadata first to retrieve file name
        DocumentResponse document = documentService.getDocumentById(documentId);
        
        // Download the file resource
        Resource resource = documentService.downloadDocument(documentId);

        // Determine content type based on file extension
        String contentType = contentTypeService.getContentType(document.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "inline; filename=\"" + document.getFileName() + "\"")
                .body(resource);
    }

    /**
     * Deletes a document
     * Requires ADMIN role
     * @param documentId the ID of the document to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{documentId}")
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Answers a question about a specific document
     * Available to all authenticated users
     * @param request the question request containing documentId and question
     * @return ResponseEntity containing the answer
     */
    @PostMapping("/ask")
    public ResponseEntity<QuestionResponse> askQuestion(
            @Valid @RequestBody QuestionRequest request) {
        QuestionResponse response = documentQAService.answerQuestion(request);
        return ResponseEntity.ok(response);
    }
}

