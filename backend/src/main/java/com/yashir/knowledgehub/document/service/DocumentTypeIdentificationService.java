package com.yashir.knowledgehub.document.service;

import com.yashir.knowledgehub.document.model.DocumentType;
import com.yashir.knowledgehub.llm.service.LLMServiceFactory;
import com.yashir.knowledgehub.llm.service.LLMServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for identifying document type using LLM
 * Delegates to configured LLM service (OpenAI or Mock)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentTypeIdentificationService {

    private final LLMServiceFactory llmServiceFactory;
    private final DocumentContentExtractionService contentExtractionService;

    /**
     * Identifies the document type based on file name and content using LLM
     * @param file the uploaded file
     * @return the identified document type
     */
    public DocumentType identifyDocumentType(MultipartFile file) {
        String fileName = file.getOriginalFilename() != null 
            ? file.getOriginalFilename() 
            : "unknown";
        
        // Extract file content using shared extraction service
        String fileContent = contentExtractionService.extractContentFromMultipartFile(file, fileName);
        
        // Get the configured LLM service and use it to identify document type
        LLMServiceInterface llmService = llmServiceFactory.getLLMService();
        DocumentType documentType = llmService.identifyDocumentType(fileName, fileContent);
        
        return documentType;
    }
}

