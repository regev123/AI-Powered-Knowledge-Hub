package com.yashir.knowledgehub.document.mapper;

import com.yashir.knowledgehub.document.dto.DocumentResponse;
import com.yashir.knowledgehub.document.model.Document;
import org.springframework.stereotype.Component;

/**
 * Mapper implementation for Document entity and DTOs
 * Handles only Document mapping
 * Note: Document creation is handled in service layer, not through mapper
 */
@Component
public class DocumentMapper implements DocumentMapperInterface {

    @Override
    public DocumentResponse toDto(Document entity) {
        DocumentResponse response = new DocumentResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setType(entity.getType().name());
        response.setFileName(entity.getFileName());
        response.setUploadedBy(entity.getUploadedBy());
        response.setUploadDate(entity.getUploadDate());
        return response;
    }
}

