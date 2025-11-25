package com.yashir.knowledgehub.llm.service;

import com.yashir.knowledgehub.llm.config.OpenAIConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

/**
 * Client for making OpenAI API calls
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAIClient {

    private final WebClient.Builder webClientBuilder;
    private final OpenAIResponseParser responseParser;

    /**
     * Makes a call to OpenAI API
     * @param apiKey the OpenAI API key
     * @param requestBody the request body map
     * @return the parsed response content, or null if the call fails
     * @throws WebClientResponseException if the API call fails
     */
    @SuppressWarnings("unchecked")
    public String callAPI(String apiKey, Map<String, Object> requestBody) throws WebClientResponseException {
        WebClient webClient = webClientBuilder.build();

        Map<String, Object> response = (Map<String, Object>) webClient.post()
                .uri(OpenAIConfig.API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return responseParser.extractContent(response);
    }
}

