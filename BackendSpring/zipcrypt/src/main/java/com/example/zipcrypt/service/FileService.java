// src/main/java/com/example/fileprocessor/service/FileService.java
package com.example.zipcrypt.service;

import com.example.zipcrypt.util.AESUtil;
import com.example.zipcrypt.util.HuffmanCoding;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class FileService {

    private final CompressionService compressionService;
    private final EncryptionService encryptionService;

    public FileService(CompressionService compressionService, EncryptionService encryptionService) {
        this.compressionService = compressionService;
        this.encryptionService = encryptionService;
    }

    public Map<String, String> processFile(MultipartFile file) throws Exception {
        String originalContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Step 1: Compress the content using Huffman Coding
        String compressedContent = compressionService.compress(originalContent);

        // Step 2: Encrypt the compressed content using AES
        String encryptedContent = encryptionService.encrypt(compressedContent);

        return Map.of(
                "original", originalContent,
                "compressed", compressedContent,
                "encrypted", encryptedContent
        );
    }
}
