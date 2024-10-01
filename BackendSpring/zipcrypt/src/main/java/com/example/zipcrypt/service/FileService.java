package com.example.zipcrypt.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.zipcrypt.util.AESUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class FileService {

    private final EncryptionService encryptionService;
    private final CompressionService compressionService;

    public FileService(EncryptionService encryptionService, CompressionService compressionService) {
        this.encryptionService = encryptionService;
        this.compressionService = compressionService;
    }

    // Extract text content from the uploaded file
    public String extractTextFromFile(MultipartFile file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        return fileContent.toString();
    }

    public ResponseEntity<InputStreamResource> downloadDecryptedFile(String encryptedContent, String secret, String salt) throws Exception {
        // First decrypt the text using AES
        String decryptedContent = AESUtil.decrypt(encryptedContent, secret, salt);

        // Write the decrypted content to a file
        File decryptedFile = new File("decrypted_output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(decryptedFile))) {
            writer.write(decryptedContent);
        }

        // Return the file as a downloadable response
        InputStreamResource resource = new InputStreamResource(new FileInputStream(decryptedFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + decryptedFile.getName())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
    
    // Compress the original text using Huffman coding
    public String compressText(String originalText) {
        return compressionService.compress(originalText);
    }

    // Encrypt the compressed text using AES
    public String encryptText(String compressedText) throws Exception {
        return EncryptionService.encrypt(compressedText);
    }

    // Decrypt the encrypted text using AES
    public String decryptText(String encryptedText) throws Exception {
        return EncryptionService.decrypt(encryptedText);
    }

    // Decompress the text using Huffman coding
    public String decompressText(String compressedText) {
        return compressionService.decompress(compressedText);
    }
}
