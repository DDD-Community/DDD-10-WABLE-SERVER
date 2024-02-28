package com.wable.harmonika.domain.card.repository;

import io.jsonwebtoken.security.Keys;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;

class JasyptConfigAESTest {

    @Test
    void stringEncryptor() {
        String url = "jdbc:mysql://localhost:3306/default";
        String username = "root";
        String password = "password";

        System.out.println("ENC(" + jasyptEncoding(url) + ")");
        System.out.println("ENC(" + jasyptEncoding(username) + ")");
        System.out.println("ENC(" + jasyptEncoding(password) + ")");
    }

    public String jasyptEncoding(String value) {
        String jasyptPassword = System.getenv("ENCRYPTOR_PASSWORD");
        System.out.println(jasyptPassword);
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(jasyptPassword);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.encrypt(value);
    }

    @Test
    void TokenGenerator() {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        StringBuilder hexString = new StringBuilder();
        for (byte b : keyBytes) {
            hexString.append(String.format("%02x", b));
        }
        String keyHex = hexString.toString();
        System.out.println(keyHex);
    }
}