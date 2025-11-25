package com.yashir.knowledgehub.document.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service for extracting text content from various document formats
 * Follows Single Responsibility Principle - handles only content extraction
 * Used by both DocumentTypeIdentificationService and DocumentQAService to avoid code duplication
 */
@Service
@Slf4j
public class DocumentContentExtractionService {

    /**
     * Extracts text content from a MultipartFile (used during upload)
     * @param file the uploaded file
     * @param fileName the file name
     * @return the extracted text content, or empty string if extraction fails or file type is unsupported
     */
    public String extractContentFromMultipartFile(MultipartFile file, String fileName) {
        if (fileName == null) {
            return "";
        }

        String lowerFileName = fileName.toLowerCase();

        // Handle PDF files
        if (lowerFileName.endsWith(".pdf")) {
            return extractPdfContentFromMultipartFile(file);
        }

        // Handle Word documents (.doc and .docx)
        if (lowerFileName.endsWith(".doc")) {
            return extractDocContentFromMultipartFile(file);
        }
        if (lowerFileName.endsWith(".docx")) {
            return extractDocxContentFromMultipartFile(file);
        }

        // Handle plain text files
        if (isTextFile(fileName)) {
            try {
                return new String(file.getBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.warn("Could not read content of file {}: {}", fileName, e.getMessage());
                return "";
            }
        }

        // Return empty for unsupported file types
        return "";
    }

    /**
     * Extracts text content from a stored file (Path) - used for Q&A on existing documents
     * @param filePath the path to the stored file
     * @param fileName the file name (for determining file type)
     * @return the extracted text content, or empty string if extraction fails or file type is unsupported
     * @throws IOException if file reading fails
     */
    public String extractContentFromPath(Path filePath, String fileName) throws IOException {
        if (fileName == null) {
            return "";
        }

        if (!Files.exists(filePath)) {
            log.warn("File not found at path: {}", filePath);
            return "";
        }

        String lowerFileName = fileName.toLowerCase();

        // Handle PDF files
        if (lowerFileName.endsWith(".pdf")) {
            return extractPdfContentFromPath(filePath);
        }

        // Handle Word documents (.doc and .docx)
        if (lowerFileName.endsWith(".doc")) {
            return extractDocContentFromPath(filePath);
        }
        if (lowerFileName.endsWith(".docx")) {
            return extractDocxContentFromPath(filePath);
        }

        // Handle plain text files
        if (isTextFile(fileName)) {
            return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        }

        // Return empty for unsupported file types
        log.warn("Unsupported file type for content extraction: {}", fileName);
        return "";
    }

    /**
     * Checks if the file is a plain text file that can be read directly as UTF-8
     * @param fileName the file name
     * @return true if the file is a plain text file (.txt)
     */
    private boolean isTextFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lowerFileName = fileName.toLowerCase();
        return lowerFileName.endsWith(".txt");
    }

    // MultipartFile extraction methods

    private String extractPdfContentFromMultipartFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {
            
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return text;
        } catch (IOException e) {
            log.warn("Could not extract text from PDF file: {}", e.getMessage());
            return "";
        }
    }

    private String extractDocContentFromMultipartFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             HWPFDocument document = new HWPFDocument(inputStream);
             WordExtractor extractor = new WordExtractor(document)) {
            
            String text = extractor.getText();
            return text;
        } catch (IOException e) {
            log.warn("Could not extract text from .doc file: {}", e.getMessage());
            return "";
        }
    }

    private String extractDocxContentFromMultipartFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            
            String text = extractor.getText();
            return text;
        } catch (IOException e) {
            log.warn("Could not extract text from .docx file: {}", e.getMessage());
            return "";
        }
    }

    // Path extraction methods

    private String extractPdfContentFromPath(Path filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath);
             PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {
            
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return text;
        }
    }

    private String extractDocContentFromPath(Path filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath);
             HWPFDocument document = new HWPFDocument(inputStream);
             WordExtractor extractor = new WordExtractor(document)) {
            
            String text = extractor.getText();
            return text;
        }
    }

    private String extractDocxContentFromPath(Path filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath);
             XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            
            String text = extractor.getText();
            return text;
        }
    }
}

