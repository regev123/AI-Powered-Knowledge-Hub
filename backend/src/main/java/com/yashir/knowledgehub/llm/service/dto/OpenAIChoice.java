package com.yashir.knowledgehub.llm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO representing a choice in OpenAI API response
 */
@Data
public class OpenAIChoice {
    
    @JsonProperty("message")
    private OpenAIMessage message;
}

