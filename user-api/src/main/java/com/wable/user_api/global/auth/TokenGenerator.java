package com.wable.user_api.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    @Value("${cloud.private.group.secretKey}")
    private String secretKey;

    @Value("${cloud.private.group.validityMillis}")
    private long validityMillis;

    public String generateJwtToken() {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validityMillis;

        return Jwts.builder()
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public boolean validateJwtToken(String token) {
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
