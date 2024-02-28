package com.wable.harmonika.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class TokenGenerator {


    @Value("${private.group.secretKey}")
    private static String secretKey;

    @Value("${private.group.validityMillis}")
    private static long validityMillis;

    public static String generateJwtToken() {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validityMillis;

        return Jwts.builder()
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS256, TokenGenerator.secretKey.getBytes())
                .compact();
    }

    public static boolean validateJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            return now.before(expirationDate);

        } catch (Exception e) {
            return false;
        }
    }
}