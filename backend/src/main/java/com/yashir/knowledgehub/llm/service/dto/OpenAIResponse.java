package com.yashir.knowledgehub.llm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * DTO representing OpenAI API response structure
 * Used for type-safe JSON parsing
 */
@Data
public class OpenAIResponse {
    
    @JsonProperty("choices")
    private List<OpenAIChoice> choices;
}

