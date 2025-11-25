package com.yashir.knowledgehub.llm.service.keyword;

import com.yashir.knowledgehub.document.model.DocumentType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Keyword matcher for document type identification
 * Used by MockLLMService for keyword-based classification
 */
@Component
public class DocumentTypeKeywordMatcher {

    private static final List<String> POLICY_KEYWORDS = Arrays.asList(
            "policy", "procedure", "guideline", "rule", "regulation", "standard",
            "protocol", "directive", "compliance", "governance", "framework", 
            "code of conduct", "terms of service", "terms and conditions"
    );
    
    private static final List<String> REPORT_KEYWORDS = Arrays.asList(
            "report", "analysis", "summary", "data", "statistic", "overview", 
            "findings", "results", "evaluation", "assessment", "review", "audit", 
            "metrics", "kpi", "dashboard", "quarterly", "annual", "monthly"
    );
    
    private static final List<String> MANUAL_KEYWORDS = Arrays.asList(
            "manual", "guide", "instruction", "how to", "how-to", "tutorial",
            "walkthrough", "step by step", "step-by-step", "getting started",
            "quick start", "handbook", "reference", "documentation", "docs"
    );

    /**
     * Identifies document type based on keywords in file name and content
     * Checks file name first (faster) then falls back to content
     * @param fileName the file name
     * @param fileContent the file content
     * @return the identified DocumentType
     */
    public DocumentType identifyByKeywords(String fileName, String fileContent) {
        String lowerFileName = normalizeString(fileName);
        String lowerContent = normalizeString(fileContent);
        
        // Check filename first (faster - small string)
        DocumentType fileNameType = checkKeywords(lowerFileName);
        if (fileNameType != null) {
            return fileNameType;
        }
        
        // Fallback to file content (slower - larger string)
        DocumentType contentType = checkKeywords(lowerContent);
        if (contentType != null) {
            return contentType;
        }
        
        return DocumentType.UNDEFINED;
    }

    /**
     * Checks if text contains keywords for any document type
     * @param text the text to check
     * @return the DocumentType if keywords found, null otherwise
     */
    private DocumentType checkKeywords(String text) {
        if (containsKeywords(text, POLICY_KEYWORDS)) {
            return DocumentType.POLICY;
        }
        if (containsKeywords(text, REPORT_KEYWORDS)) {
            return DocumentType.REPORT;
        }
        if (containsKeywords(text, MANUAL_KEYWORDS)) {
            return DocumentType.MANUAL;
        }
        return null;
    }

    /**
     * Normalizes a string for keyword matching
     * @param text the text to normalize
     * @return normalized lowercase string, or empty string if null
     */
    private String normalizeString(String text) {
        return text != null ? text.toLowerCase(Locale.ROOT) : "";
    }

    /**
     * Checks if text contains any of the given keywords
     * @param text the text to search
     * @param keywords the keywords to search for
     * @return true if any keyword is found, false otherwise
     */
    private boolean containsKeywords(String text, List<String> keywords) {
        return keywords.stream().anyMatch(text::contains);
    }
}

