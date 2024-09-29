// src/main/java/com/example/fileprocessor/service/EncryptionService.java
package com.example.zipcrypt.service;

import com.example.zipcrypt.util.AESUtil;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private static final String SECRET_KEY = "aesEncryptionKey";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    public String encrypt(String data) throws Exception {
        return AESUtil.encrypt(data, SECRET_KEY, SALT);
    }
}
