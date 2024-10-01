package com.example.zipcrypt.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtil {

    // Encryption method
    public static String encrypt(String strToEncrypt, String secret, String salt) throws Exception {
        byte[] iv = new byte[16];  // Initialize with 16-byte IV for AES
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Create a SecretKey using PBKDF2WithHmacSHA256
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKeySpec secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        // Initialize Cipher for AES encryption in CBC mode with PKCS5 padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

        // Perform encryption and return the result as a Base64 encoded string
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    // Decryption method
    public static String decrypt(String strToDecrypt, String secret, String salt) throws Exception {
        byte[] iv = new byte[16];  // Initialize with 16-byte IV for AES
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Create a SecretKey using PBKDF2WithHmacSHA256
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKeySpec secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        // Initialize Cipher for AES decryption in CBC mode with PKCS5 padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

        // Perform decryption and return the result as a string
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
