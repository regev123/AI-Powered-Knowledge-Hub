package com.yashir.knowledgehub.document.service;

import com.yashir.knowledgehub.document.config.CacheConfig;
import com.yashir.knowledgehub.document.dto.QuestionRequest;
import com.yashir.knowledgehub.document.dto.QuestionResponse;
import com.yashir.knowledgehub.document.exception.DocumentNotFoundException;
import com.yashir.knowledgehub.document.exception.DocumentProcessingException;
import com.yashir.knowledgehub.document.model.Document;
import com.yashir.knowledgehub.document.repository.DocumentRepository;
import com.yashir.knowledgehub.llm.service.LLMServiceFactory;
import com.yashir.knowledgehub.llm.service.LLMServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service for handling Question & Answer operations on documents
 * Handles document content extraction and LLM-based Q&A
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentQAService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final DocumentContentExtractionService contentExtractionService;
    private final LLMServiceFactory llmServiceFactory;

    /**
     * Answers a question about a specific document
     * Uses caching to avoid redundant OpenAI API calls for similar questions
     * Cache key is generated using normalized question to catch variations
     * 
     * HOW CACHING WORKS:
     * Spring Cache (@Cacheable) intercepts this method call using AOP:
     * 1. Before method execution: Generates cache key and checks cache
     * 2. If cache HIT: Returns cached value immediately (this method body NEVER executes)
     * 3. If cache MISS: Executes this method, stores result in cache, then returns result
     * 
     * @param request the question request containing document ID and question
     * @return the answer response (from cache if available, otherwise from OpenAI)
     */
    @Transactional(readOnly = true)
    @Cacheable(value = CacheConfig.QA_CACHE_NAME, 
               key = "T(com.yashir.knowledgehub.document.util.QuestionNormalizer).generateCacheKey(#request.documentId, #request.question)")
    public QuestionResponse answerQuestion(QuestionRequest request) {
        // NOTE: This code only executes on CACHE MISS
        // If cache HIT occurs, Spring returns cached value and this method never runs
        
        // 1. Get document by ID
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundException(request.getDocumentId()));

        // 2. Extract document content from file
        String documentContent = extractDocumentContent(document);
        
        // 3. Build context: document name + content
        String documentContext = buildDocumentContext(document.getName(), documentContent);

        // 4. Get LLM service and ask question (THIS IS THE EXPENSIVE OPERATION)
        LLMServiceInterface llmService = llmServiceFactory.getLLMService();
        String answer = llmService.answerQuestion(request.getQuestion(), documentContext, document.getType());

        // 5. Return response (Spring Cache will automatically store this in cache)
        return new QuestionResponse(answer);
    }

    /**
     * Extracts text content from a stored document file
     * @param document the document entity
     * @return the extracted text content
     */
    private String extractDocumentContent(Document document) {
        try {
            Path filePath = fileStorageService.getFilePath(document.getFilePath());
            
            if (!Files.exists(filePath)) {
                throw new DocumentProcessingException("Document file not found: " + document.getFilePath());
            }

            String fileName = document.getFileName();
            if (fileName == null) {
                throw new DocumentProcessingException("Document file name is null");
            }

            // Use shared extraction service
            String content = contentExtractionService.extractContentFromPath(filePath, fileName);
            
            if (content == null || content.isEmpty()) {
                log.warn("No content extracted from document {}: {}", document.getId(), fileName);
            }
            
            return content != null ? content : "";

        } catch (IOException e) {
            log.error("Failed to extract content from document {}: {}", document.getId(), e.getMessage(), e);
            throw new DocumentProcessingException("Failed to extract document content: " + e.getMessage(), e);
        }
    }

    /**
     * Builds document context string combining title and content
     * @param documentName the document name/title
     * @param documentContent the document content
     * @return formatted context string
     */
    private String buildDocumentContext(String documentName, String documentContent) {
        StringBuilder context = new StringBuilder();
        context.append("Document Title: ").append(documentName).append("\n\n");
        context.append("Document Content:\n");
        context.append("---\n");
        context.append(documentContent);
        context.append("\n---\n");
        return context.toString();
    }
}
