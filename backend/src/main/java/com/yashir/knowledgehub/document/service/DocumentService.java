package com.yashir.knowledgehub.document.service;

import com.yashir.knowledgehub.document.config.CacheConfig;
import com.yashir.knowledgehub.document.dto.DocumentResponse;
import com.yashir.knowledgehub.document.dto.DocumentUploadRequest;
import com.yashir.knowledgehub.document.mapper.DocumentMapperInterface;
import com.yashir.knowledgehub.document.model.Document;
import com.yashir.knowledgehub.document.model.DocumentType;
import com.yashir.knowledgehub.document.repository.DocumentRepository;
import com.yashir.knowledgehub.document.exception.DocumentAlreadyExistsException;
import com.yashir.knowledgehub.document.exception.DocumentNotFoundException;
import com.yashir.knowledgehub.document.exception.DocumentProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for Document operations
 * Handles only document business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService implements DocumentServiceInterface {

    private final DocumentRepository documentRepository;
    private final DocumentMapperInterface documentMapper;
    private final FileStorageService fileStorageService;
    private final DocumentTypeIdentificationService typeIdentificationService;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documentMapper.toDtoList(documents);
    }

    @Override
    @Transactional
    public DocumentResponse uploadDocument(DocumentUploadRequest request, String uploadedBy) {
        try {
            // 1. Extract file from request
            MultipartFile file = request.getFile();
            String fileName = file.getOriginalFilename();
            
            // Validate that document with same file name doesn't already exist
            validateDocumentDoesNotExist(fileName);
            
            // 2. Save file to disk
            String filePath = fileStorageService.saveFile(file);
            
            // 3. Identify document type (Policy, Report, Manual)
            DocumentType documentType = typeIdentificationService.identifyDocumentType(file);
            
            // 4. Extract document name from file name
            String documentName = fileStorageService.extractDocumentName(fileName);
            
            // 5. Save document metadata to database
            Document document = new Document();
            document.setName(documentName);
            document.setType(documentType);
            document.setFileName(fileName);
            document.setFilePath(filePath);
            document.setUploadedBy(uploadedBy);
            document.setUploadDate(LocalDateTime.now());
            
            Document savedDocument = documentRepository.save(document);
            
            // 6. Return DocumentResponse
            return documentMapper.toDto(savedDocument);
            
        } catch (IOException e) {
            throw new DocumentProcessingException("Failed to save file: " + e.getMessage(), e);
        }
    }

    /**
     * Validates that a document with the given file name does not already exist
     * @param fileName the file name to validate
     * @throws DocumentAlreadyExistsException if a document with the file name already exists
     */
    private void validateDocumentDoesNotExist(String fileName) {
        if (fileName != null && documentRepository.existsByFileName(fileName)) {
            throw new DocumentAlreadyExistsException(fileName);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadDocument(Long documentId) {
        // Find document by ID
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        try {
            // Get file path and load as resource
            Path filePath = fileStorageService.getFilePath(document.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            // Check if file exists and is readable
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new DocumentProcessingException("File not found or not readable: " + document.getFilePath());
            }
        } catch (IOException e) {
            throw new DocumentProcessingException("Failed to load file: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocumentById(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));
        return documentMapper.toDto(document);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.QA_CACHE_NAME, allEntries = true)
    public void deleteDocument(Long documentId) {
        // Find document by ID
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        try {
            // Delete file from disk
            fileStorageService.deleteFile(document.getFilePath());
        } catch (IOException e) {
            log.warn("Failed to delete file from disk: {}", document.getFilePath(), e);
            // Continue with database deletion even if file deletion fails
        }

        // Delete document from database
        documentRepository.delete(document);
    }
}

