package com.yashir.knowledgehub.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for document response data
 * Used for returning document information in API responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private Long id;
    private String name;
    private String type;
    private String fileName;
    private String uploadedBy;
    private LocalDateTime uploadDate;
}

