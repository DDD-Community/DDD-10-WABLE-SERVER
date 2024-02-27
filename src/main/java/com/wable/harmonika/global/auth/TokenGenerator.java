package com.wable.harmonika.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class TokenGenerator {

    private static String secretKey = "1234563923842039482304982384187342839573490804356389475892374981347";
    private static long validityMillis = 259200000;

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