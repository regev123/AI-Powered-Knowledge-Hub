package com.yashir.knowledgehub.document.mapper;

import com.yashir.knowledgehub.document.dto.DocumentResponse;
import com.yashir.knowledgehub.document.model.Document;
import com.yashir.knowledgehub.mapper.EntityToDtoMapper;

/**
 * Mapper interface for Document entity and DTOs
 * Extends base mapper interfaces following Interface Segregation Principle
 * Note: Document creation is handled in service layer, not through mapper
 */
public interface DocumentMapperInterface extends 
        EntityToDtoMapper<Document, DocumentResponse> {
}

