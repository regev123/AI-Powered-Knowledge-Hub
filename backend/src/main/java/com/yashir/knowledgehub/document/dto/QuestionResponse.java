package com.yashir.knowledgehub.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for question response
 * Contains the answer to a question about a document
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private String answer;
}

