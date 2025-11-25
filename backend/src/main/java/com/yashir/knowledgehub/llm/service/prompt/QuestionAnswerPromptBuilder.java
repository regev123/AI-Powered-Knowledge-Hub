package com.yashir.knowledgehub.llm.service.prompt;

import com.yashir.knowledgehub.document.model.DocumentType;
import org.springframework.stereotype.Component;

/**
 * Builder for question answering prompts
 * Supports document-type-specific behavior for POLICY, REPORT, and MANUAL documents
 */
@Component
public class QuestionAnswerPromptBuilder {

    /**
     * Builds a prompt for question answering with document-type-specific behavior
     * @param question the user's question
     * @param documentContext the document context (title + content)
     * @param documentType the type of document (POLICY, REPORT, MANUAL, UNDEFINED)
     * @return the formatted prompt string
     */
    public String buildPrompt(String question, String documentContext, DocumentType documentType) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("You are a helpful assistant that answers questions based on provided document content.\n\n");
        prompt.append("DOCUMENT CONTEXT:\n");
        prompt.append(documentContext);
        prompt.append("\n\n");
        prompt.append("QUESTION: ");
        prompt.append(question);
        prompt.append("\n\n");
        appendInstructions(prompt, documentType);
        
        return prompt.toString();
    }

    private void appendInstructions(StringBuilder prompt, DocumentType documentType) {
        prompt.append("INSTRUCTIONS:\n");
        prompt.append("- Answer the question based ONLY on the information provided in the document context above.\n");
        prompt.append("- If the answer cannot be found in the document, say \"The answer is not found in the document.\"\n");
        prompt.append("- Provide a clear, concise, and accurate answer.\n");
        prompt.append("- If the question is unclear or unrelated to the document, politely indicate that.\n");
        
        appendDocumentTypeSpecificInstructions(prompt, documentType);
    }

    private void appendDocumentTypeSpecificInstructions(StringBuilder prompt, DocumentType documentType) {
        switch (documentType) {
            case POLICY:
                prompt.append("\nSPECIAL BEHAVIOR FOR POLICY DOCUMENTS:\n");
                prompt.append("- Focus on sections and headers when answering.\n");
                prompt.append("- Reference specific policy sections when relevant.\n");
                prompt.append("- Structure your answer to align with the document's organizational structure.\n");
                break;
                
            case REPORT:
                prompt.append("\nSPECIAL BEHAVIOR FOR REPORT DOCUMENTS:\n");
                prompt.append("- Include summaries or highlighted data points when relevant.\n");
                prompt.append("- Reference specific statistics, metrics, or findings from the report.\n");
                prompt.append("- Provide quantitative information when available.\n");
                break;
                
            case MANUAL:
                prompt.append("\nSPECIAL BEHAVIOR FOR MANUAL DOCUMENTS:\n");
                prompt.append("- Include practical steps when answering how-to questions.\n");
                prompt.append("- Provide step-by-step instructions when relevant.\n");
                prompt.append("- Focus on actionable guidance and procedures.\n");
                break;
                
            case UNDEFINED:
            default:
                // No special behavior for undefined document types
                break;
        }
    }
}

