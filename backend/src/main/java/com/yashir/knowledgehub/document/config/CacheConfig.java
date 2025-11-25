package com.yashir.knowledgehub.document.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache configuration for Q&A service
 * Uses Caffeine for in-memory caching with TTL
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String QA_CACHE_NAME = "qaCache";

    /**
     * Configures Caffeine cache manager for Q&A responses
     * Cache settings:
     * - Maximum 1000 entries
     * - TTL: 24 hours
     * - Eviction policy: Time-based expiration
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(QA_CACHE_NAME);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000) // Maximum 1000 cached Q&A pairs
                .expireAfterWrite(24, TimeUnit.HOURS) // Cache expires after 24 hours
                .recordStats() // Enable cache statistics
        );
        return cacheManager;
    }
}

