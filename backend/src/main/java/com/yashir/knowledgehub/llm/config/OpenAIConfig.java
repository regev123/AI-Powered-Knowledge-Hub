package com.yashir.knowledgehub.llm.config;

/**
 * Constants for OpenAI API configuration
 */
public final class OpenAIConfig {

    /**
     * OpenAI API endpoint URL
     */
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Default OpenAI model to use
     */
    public static final String DEFAULT_MODEL = "gpt-5.1";

    /**
     * Maximum completion tokens for document type identification
     */
    public static final int MAX_TOKENS_TYPE_IDENTIFICATION = 50;

    /**
     * Maximum completion tokens for question answering
     */
    public static final int MAX_TOKENS_QA = 500;

    /**
     * Temperature for document type identification (lower = more deterministic)
     */
    public static final double TEMPERATURE_TYPE_IDENTIFICATION = 0.3;

    /**
     * Temperature for question answering (higher = more natural)
     */
    public static final double TEMPERATURE_QA = 0.7;

    /**
     * Maximum content length to send for document type identification (to save tokens)
     */
    public static final int MAX_CONTENT_LENGTH = 3000;
}

