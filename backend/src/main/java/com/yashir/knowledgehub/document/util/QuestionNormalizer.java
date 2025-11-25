package com.yashir.knowledgehub.document.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for normalizing questions to improve cache hit rate
 * Handles variations in punctuation, whitespace, and stop words
 */
public class QuestionNormalizer {

    // Common English stop words to remove for better cache matching
    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
            "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
            "to", "was", "will", "with", "what", "which", "who", "whom",
            "this", "these", "those", "tell", "me", "about", "can", "you",
            "please", "how", "do", "does", "did", "would", "could", "should"
    );

    /**
     * Normalizes a question string for cache key generation
     * - Lowercase conversion
     * - Trim whitespace
     * - Remove punctuation
     * - Normalize multiple spaces
     * - Remove stop words
     * 
     * @param question the original question
     * @return normalized question string
     */
    public static String normalize(String question) {
        if (question == null || question.trim().isEmpty()) {
            return "";
        }

        // Level 1: Basic normalization
        String normalized = question.toLowerCase()
                .trim()
                .replaceAll("[?!.,;:()\\[\\]{}'\"`]", "") // Remove punctuation
                .replaceAll("\\s+", " ") // Normalize multiple spaces to single space
                .trim();

        // Level 2: Remove stop words
        String[] words = normalized.split("\\s+");
        String result = Arrays.stream(words)
                .filter(word -> !word.isEmpty() && !STOP_WORDS.contains(word))
                .collect(Collectors.joining(" "))
                .trim();

        return result.isEmpty() ? normalized : result; // Fallback to normalized if all words removed
    }

    /**
     * Generates a cache key for a question and document ID
     * @param documentId the document ID
     * @param question the question
     * @return normalized cache key
     */
    public static String generateCacheKey(Long documentId, String question) {
        String normalizedQuestion = normalize(question);
        return String.format("qa:doc:%d:q:%s", documentId, normalizedQuestion);
    }
}

