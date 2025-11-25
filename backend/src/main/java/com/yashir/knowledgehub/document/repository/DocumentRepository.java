package com.yashir.knowledgehub.document.repository;

import com.yashir.knowledgehub.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Document entity
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    /**
     * Checks if a document exists with the given file name
     * @param fileName the file name
     * @return true if document exists, false otherwise
     */
    boolean existsByFileName(String fileName);
}

