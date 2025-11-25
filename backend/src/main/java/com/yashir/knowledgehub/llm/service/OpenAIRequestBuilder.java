package com.yashir.knowledgehub.llm.service;

import com.yashir.knowledgehub.llm.config.OpenAIConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for OpenAI API requests
 */
@Component
public class OpenAIRequestBuilder {

    /**
     * Builds a request for document type identification
     * @param prompt the prompt to send
     * @return the request body map
     */
    public Map<String, Object> buildTypeIdentificationRequest(String prompt) {
        return buildRequest(prompt, OpenAIConfig.MAX_TOKENS_TYPE_IDENTIFICATION, OpenAIConfig.TEMPERATURE_TYPE_IDENTIFICATION);
    }

    /**
     * Builds a request for question answering
     * @param prompt the prompt to send
     * @return the request body map
     */
    public Map<String, Object> buildQARequest(String prompt) {
        return buildRequest(prompt, OpenAIConfig.MAX_TOKENS_QA, OpenAIConfig.TEMPERATURE_QA);
    }

    /**
     * Builds a generic OpenAI API request
     * @param prompt the prompt to send
     * @param maxTokens maximum completion tokens
     * @param temperature the temperature setting
     * @return the request body map
     */
    private Map<String, Object> buildRequest(String prompt, int maxTokens, double temperature) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", OpenAIConfig.DEFAULT_MODEL);
        
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        
        requestBody.put("messages", new Object[]{message});
        requestBody.put("max_completion_tokens", maxTokens);
        requestBody.put("temperature", temperature);
        
        return requestBody;
    }
}

