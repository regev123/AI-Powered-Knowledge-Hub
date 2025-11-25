package com.yashir.knowledgehub.llm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashir.knowledgehub.document.model.DocumentType;
import com.yashir.knowledgehub.llm.service.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Parser for OpenAI API responses
 * Uses Jackson ObjectMapper for type-safe JSON parsing
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAIResponseParser {

    private final ObjectMapper objectMapper;

    /**
     * Extracts content from OpenAI API response using type-safe parsing
     * @param response the raw API response map
     * @return the content string, or null if extraction fails
     */
    public String extractContent(Map<String, Object> response) {
        try {
            OpenAIResponse openAIResponse = objectMapper.convertValue(response, OpenAIResponse.class);
            
            if (openAIResponse.getChoices() == null || openAIResponse.getChoices().isEmpty()) {
                log.warn("OpenAI API returned empty choices list. Raw response: {}", response);
                return null;
            }
            
            var firstChoice = openAIResponse.getChoices().get(0);
            if (firstChoice.getMessage() == null) {
                log.warn("OpenAI API returned null message in first choice. Raw choice: {}", firstChoice);
                return null;
            }
            
            return firstChoice.getMessage().getContent();
        } catch (IllegalArgumentException e) {
            log.error("Failed to parse OpenAI API response: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Parses OpenAI response to DocumentType
     * The prompt instructs OpenAI to return ONLY the category name, so we trust that and parse directly
     * @param response the raw response string from OpenAI
     * @return the parsed DocumentType, or UNDEFINED if parsing fails
     */
    public DocumentType parseDocumentType(String response) {
        if (response == null || response.trim().isEmpty()) {
            return DocumentType.UNDEFINED;
        }
        
        // Clean the response: trim whitespace, uppercase, remove punctuation
        String cleaned = response.trim().toUpperCase().replaceAll("[^A-Z]", "");
        
        try {
            return DocumentType.valueOf(cleaned);
        } catch (IllegalArgumentException e) {
            log.warn("OpenAI returned unrecognized document type: '{}' (cleaned: '{}'). Returning UNDEFINED.",
                    response, cleaned);
            return DocumentType.UNDEFINED;
        }
    }
}

