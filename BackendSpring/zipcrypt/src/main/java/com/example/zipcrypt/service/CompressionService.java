// src/main/java/com/example/fileprocessor/service/CompressionService.java
package com.example.zipcrypt.service;

import com.example.zipcrypt.util.HuffmanCoding;
import org.springframework.stereotype.Service;

@Service
public class CompressionService {

    public String compress(String input) {
        return HuffmanCoding.compress(input);
    }

    public String decompress(String compressedText){
        return HuffmanCoding.decompress(compressedText);
    }
}
