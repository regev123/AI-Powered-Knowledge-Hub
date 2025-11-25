package com.yashir.knowledgehub.llm.service;

import com.yashir.knowledgehub.document.model.DocumentType;

/**
 * Interface for LLM services to identify document types and answer questions
 */
public interface LLMServiceInterface {
    
    /**
     * Identifies the document type using LLM based on file content
     * @param fileName the name of the file
     * @param fileContent the content of the file (can be empty for binary files)
     * @return the identified DocumentType
     */
    DocumentType identifyDocumentType(String fileName, String fileContent);
    
    /**
     * Answers a question about a document using LLM
     * @param question the question to ask
     * @param documentContext the document context (title + content)
     * @param documentType the type of document (POLICY, REPORT, MANUAL, UNDEFINED)
     * @return the answer to the question
     */
    String answerQuestion(String question, String documentContext, DocumentType documentType);
}

