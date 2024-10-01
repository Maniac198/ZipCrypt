package com.example.zipcrypt.controller;

import com.example.zipcrypt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/compress-encrypt")
    public ResponseEntity<?> compressAndEncryptFile(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            // Process file using service
            String originalText = fileService.extractTextFromFile(file);
            String compressedText = fileService.compressText(originalText);
            String encryptedText = fileService.encryptText(compressedText);

            // Write the encrypted data to a file
            File encryptedFile = new File("encrypted.txt");
            try (FileOutputStream encryptedOut = new FileOutputStream(encryptedFile)) {
                encryptedOut.write(encryptedText.getBytes());
            }

            // Return the download URL for the encrypted file
            return ResponseEntity.ok().body(new String[]{
                "File encrypted successfully",
                "/api/download/encrypted.txt"
            });

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed");
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptFile(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            String encryptedText = new String(file.getBytes());
            String decryptedText = fileService.decryptText(encryptedText);
            String originalText = fileService.decompressText(decryptedText);

            return ResponseEntity.ok().body(originalText);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Decryption failed");
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("filename") String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] fileData = java.nio.file.Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @PostMapping("/decrypt/download")
    public ResponseEntity<InputStreamResource> downloadDecryptedFile(@RequestParam("encryptedContent") String encryptedContent,
                                                                     @RequestParam("secret") String secret,
                                                                     @RequestParam("salt") String salt) throws Exception {
        return fileService.downloadDecryptedFile(encryptedContent, secret, salt);
    }
}
