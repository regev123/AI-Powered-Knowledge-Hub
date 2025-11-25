package com.yashir.knowledgehub.llm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO representing a message in OpenAI API response
 */
@Data
public class OpenAIMessage {
    
    @JsonProperty("content")
    private String content;
}

