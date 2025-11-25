package com.yashir.knowledgehub.llm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Factory for creating LLM service instances based on configuration
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LLMServiceFactory {

    @Value("${llm.provider:mock}")
    private String providerString;
    
    private final OpenAIService openAIService;
    private final MockLLMService mockLLMService;

    /**
     * Gets the appropriate LLM service based on configuration
     * Reads provider from application.yml
     * @return the configured LLM service implementation
     */
    public LLMServiceInterface getLLMService() {
        if (providerString != null && providerString.equals("openai")) {
            return openAIService;
        }
        return mockLLMService;
    }
}

