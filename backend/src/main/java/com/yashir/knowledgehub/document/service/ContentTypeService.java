package com.yashir.knowledgehub.document.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for determining content types based on file extensions
 * Follows Open/Closed Principle - can be extended without modification
 */
@Service
public class ContentTypeService {

    private static final Map<String, String> CONTENT_TYPE_MAP = Map.of(
            ".pdf", "application/pdf",
            ".txt", "text/plain",
            ".doc", "application/msword",
            ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    /**
     * Determines the content type based on file extension
     * @param fileName the file name
     * @return the content type, or APPLICATION_OCTET_STREAM if unknown
     */
    public String getContentType(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        String lowerFileName = fileName.toLowerCase();
        
        // Efficient lookup: check extensions in order of likelihood
        // Most common extensions checked first
        if (lowerFileName.endsWith(".pdf")) {
            return CONTENT_TYPE_MAP.get(".pdf");
        }
        if (lowerFileName.endsWith(".docx")) {
            return CONTENT_TYPE_MAP.get(".docx");
        }
        if (lowerFileName.endsWith(".doc")) {
            return CONTENT_TYPE_MAP.get(".doc");
        }
        if (lowerFileName.endsWith(".txt")) {
            return CONTENT_TYPE_MAP.get(".txt");
        }

        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}

