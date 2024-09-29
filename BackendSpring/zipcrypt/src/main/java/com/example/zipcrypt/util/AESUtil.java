// src/main/java/com/example/fileprocessor/util/AESUtil.java
package com.example.zipcrypt.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;

public class AESUtil {

    public static String encrypt(String strToEncrypt, String secret, String salt) throws Exception {
        byte[] iv = new byte[16];
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKeySpec secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }
}
