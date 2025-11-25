package com.yashir.knowledgehub.llm.service;

import com.yashir.knowledgehub.document.model.DocumentType;
import com.yashir.knowledgehub.llm.service.prompt.DocumentTypePromptBuilder;
import com.yashir.knowledgehub.llm.service.prompt.QuestionAnswerPromptBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * OpenAI service implementation for LLM operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService implements LLMServiceInterface {
    
    @Value("${llm.openai.api.key:}")
    private String apiKey;
    
    private final OpenAIClient openAIClient;
    private final OpenAIRequestBuilder requestBuilder;
    private final OpenAIResponseParser responseParser;
    private final DocumentTypePromptBuilder documentTypePromptBuilder;
    private final QuestionAnswerPromptBuilder qaPromptBuilder;

    @Override
    public DocumentType identifyDocumentType(String fileName, String fileContent) {
        String apiKey = validateAndGetApiKey();
        if (apiKey == null) {
            return DocumentType.UNDEFINED;
        }

        try {
            String prompt = documentTypePromptBuilder.buildPrompt(fileName, fileContent);
            Map<String, Object> requestBody = requestBuilder.buildTypeIdentificationRequest(prompt);
            String response = openAIClient.callAPI(apiKey, requestBody);
            return responseParser.parseDocumentType(response);
        } catch (Exception e) {
            log.error("Error calling OpenAI API: {}", e.getMessage(), e);
            return DocumentType.UNDEFINED;
        }
    }

    /**
     * Validates and retrieves the OpenAI API key from configuration
     * Logs a warning if the API key is not configured
     * @return the API key if valid, null otherwise
     */
    private String validateAndGetApiKey() {
        if (apiKey == null || apiKey.isEmpty()) {
            log.warn("OpenAI API key is not configured.");
            return null;
        }
        return apiKey;
    }


    @Override
    public String answerQuestion(String question, String documentContext, DocumentType documentType) {
        String apiKey = validateAndGetApiKey();
        if (apiKey == null) {
            return "OpenAI API key is not configured. Please configure the API key to get answers.";
        }

        try {
            String prompt = qaPromptBuilder.buildPrompt(question, documentContext, documentType);
            Map<String, Object> requestBody = requestBuilder.buildQARequest(prompt);
            String response = openAIClient.callAPI(apiKey, requestBody);
            return formatAnswer(response);
        } catch (Exception e) {
            log.error("Error calling OpenAI API for Q&A: {}", e.getMessage(), e);
            return "An unexpected error occurred. Please try again later.";
        }
    }

    /**
     * Formats the answer response, handling null or empty responses
     * @param response the raw response from OpenAI
     * @return the formatted answer, or a default message if response is invalid
     */
    private String formatAnswer(String response) {
        if (response != null && !response.trim().isEmpty()) {
            return response.trim();
        }
        return "Sorry, I couldn't generate an answer. Please try again.";
    }
}

