package com.yashir.knowledgehub.document.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for question request
 * Used when asking questions about a document
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    
    @NotNull(message = "Document ID is required")
    private Long documentId;
    
    @NotBlank(message = "Question cannot be empty")
    private String question;
}

