package com.yashir.knowledgehub.document.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service for handling file storage operations
 */
@Service
@Slf4j
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    /**
     * Saves a file to disk and returns the file path
     * @param file the file to save
     * @return the path where the file was saved
     * @throws IOException if file saving fails
     */
    public String saveFile(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        // This creates the directory relative to the current working directory (usually project root or backend folder)
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique file name to avoid conflicts
        String originalFileName = file.getOriginalFilename();
        String fileExtension = extractFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Save file
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return UPLOAD_DIR + "/" + uniqueFileName;
    }

    /**
     * Extracts file extension from file name
     * @param fileName the file name
     * @return the file extension including the dot, or empty string if no extension
     */
    private String extractFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Extracts document name from file name (removes extension)
     * @param fileName the original file name
     * @return the document name without extension
     */
    public String extractDocumentName(String fileName) {    
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * Gets the file path for a given file path string
     * @param filePath the file path stored in database
     * @return the Path object for the file
     */
    public Path getFilePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    /**
     * Deletes a file from disk
     * @param filePath the file path to delete
     * @throws IOException if file deletion fails
     */
    public void deleteFile(String filePath) throws IOException {
        Path path = getFilePath(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        } else {
            log.warn("File not found for deletion: {}", path);
        }
    }
}

