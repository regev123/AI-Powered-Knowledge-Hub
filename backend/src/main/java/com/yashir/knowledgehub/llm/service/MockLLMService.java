package com.yashir.knowledgehub.llm.service;

import com.yashir.knowledgehub.document.model.DocumentType;
import com.yashir.knowledgehub.llm.service.keyword.DocumentTypeKeywordMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mock LLM service for testing and development
 * Uses keyword-based classification for document type identification
 * Allows the system to work without API keys
 */
@Service
@RequiredArgsConstructor
public class MockLLMService implements LLMServiceInterface {

    private static final String MOCK_ANSWER_TEMPLATE = 
            "This is a mock answer from the Mock LLM service. " +
            "The question was: \"%s\". " +
            "In a real implementation, this would use OpenAI to analyze the document context and provide an accurate answer.";

    private final DocumentTypeKeywordMatcher keywordMatcher;

    @Override
    public DocumentType identifyDocumentType(String fileName, String fileContent) {
        return keywordMatcher.identifyByKeywords(fileName, fileContent);
    }

    @Override
    public String answerQuestion(String question, String documentContext, DocumentType documentType) {
        return String.format(MOCK_ANSWER_TEMPLATE, question);
    }
}

