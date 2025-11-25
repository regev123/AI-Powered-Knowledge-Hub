package com.yashir.knowledgehub.document.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class for cleaning uploads folder on application startup
 */
@Configuration
@Slf4j
public class UploadsFolderCleaner {

    private static final String UPLOAD_DIR = "uploads";

    /**
     * Deletes the uploads folder on application startup
     * Runs after the application context is fully loaded
     */
    @Bean
    public ApplicationRunner cleanUploadsFolder() {
        return args -> {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
                
                if (Files.exists(uploadPath)) {
                    // Delete all files in the directory
                    Files.walk(uploadPath)
                            .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                            .forEach(path -> {
                                try {
                                    Files.delete(path);
                                } catch (IOException e) {
                                    log.warn("Failed to delete: {}", path, e);
                                }
                            });
                }
            } catch (IOException e) {
                log.error("Error cleaning uploads folder on startup", e);
            }
        };
    }
}

