package com.yashir.knowledgehub.llm.service.prompt;

import com.yashir.knowledgehub.llm.config.OpenAIConfig;
import org.springframework.stereotype.Component;

/**
 * Builder for document type identification prompts
 */
@Component
public class DocumentTypePromptBuilder {

    /**
     * Builds a professional prompt for OpenAI document classification
     * @param fileName the document file name
     * @param contentPreview the document content preview (may be truncated)
     * @return the formatted prompt string
     */
    public String buildPrompt(String fileName, String contentPreview) {
        StringBuilder prompt = new StringBuilder();
        
        // Clear task definition
        prompt.append("You are a document classification expert. Analyze the provided document and classify it into one of the following categories:\n\n");
        
        // Detailed category definitions
        appendCategoryDefinitions(prompt);
        
        // Classification criteria
        appendClassificationCriteria(prompt);
        
        // Document information
        appendDocumentInformation(prompt, fileName, contentPreview);
        
        // Clear output instruction
        appendOutputInstruction(prompt);
        
        return prompt.toString();
    }

    private void appendCategoryDefinitions(StringBuilder prompt) {
        prompt.append("CATEGORIES:\n");
        prompt.append("1. POLICY - Documents that define rules, procedures, guidelines, regulations, standards, protocols, or organizational policies. ");
        prompt.append("Examples: Refund Policy, Information Security Procedures, Code of Conduct, Terms of Service, Compliance Guidelines.\n");
        prompt.append("2. REPORT - Documents presenting data, statistics, analyses, findings, evaluations, or summaries. ");
        prompt.append("Examples: Quarterly Sales Report, System Performance Report, Annual Review, Audit Report, Data Analysis.\n");
        prompt.append("3. MANUAL - Documents providing instructions, guides, tutorials, or step-by-step procedures. ");
        prompt.append("Examples: User Guide, Operating Instructions, Training Manual, How-To Guide, Technical Documentation.\n");
        prompt.append("4. UNDEFINED - Documents that do not clearly fit into any of the above categories, or cannot be determined.\n\n");
    }

    private void appendClassificationCriteria(StringBuilder prompt) {
        prompt.append("CLASSIFICATION CRITERIA:\n");
        prompt.append("- Consider both the document name and content when making your decision.\n");
        prompt.append("- Look for keywords, structure, and purpose indicators.\n");
        prompt.append("- If the document contains multiple elements, classify based on the primary purpose.\n");
        prompt.append("- When uncertain, choose UNDEFINED.\n\n");
    }

    private void appendDocumentInformation(StringBuilder prompt, String fileName, String contentPreview) {
        prompt.append("DOCUMENT INFORMATION:\n");
        prompt.append("File Name: ").append(fileName).append("\n");
        
        if (contentPreview != null && !contentPreview.isEmpty()) {
            String limitedContent = truncateContent(contentPreview);
            prompt.append("\nDocument Content Preview:\n");
            prompt.append("---\n");
            prompt.append(limitedContent);
            prompt.append("\n---\n");
        } else {
            prompt.append("Content: Not available\n");
        }
    }

    private String truncateContent(String content) {
        if (content.length() > OpenAIConfig.MAX_CONTENT_LENGTH) {
            return content.substring(0, OpenAIConfig.MAX_CONTENT_LENGTH) + "\n[... content truncated ...]";
        }
        return content;
    }

    private void appendOutputInstruction(StringBuilder prompt) {
        prompt.append("\nINSTRUCTION:\n");
        prompt.append("Respond with ONLY the category name (POLICY, REPORT, MANUAL, or UNDEFINED) - no additional text, explanation, or punctuation.");
    }
}

