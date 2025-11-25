package com.yashir.knowledgehub.document.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for uploading a document
 * Contains validation annotations for input validation
 */
@Data
public class DocumentUploadRequest {
    
    @NotNull(message = "File is required")
    private MultipartFile file;
}

