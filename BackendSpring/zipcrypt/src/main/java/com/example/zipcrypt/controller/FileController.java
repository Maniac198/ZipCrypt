// src/main/java/com/example/fileprocessor/controller/FileController.java
package com.example.zipcrypt.controller;

import com.example.zipcrypt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Allow CORS from the React frontend
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/compress-encrypt")
    public ResponseEntity<Map<String, String>> compressAndEncryptFile(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> result = fileService.processFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "File processing failed: " + e.getMessage()));
        }
    }
}
