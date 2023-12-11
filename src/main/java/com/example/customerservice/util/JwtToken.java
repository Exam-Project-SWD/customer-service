package com.example.customerservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {
    private static final String SECRET_KEY = "GTKw4SbJFu2Zjm/Wr9RZU476ZcWJmZN4oTCgfJfTm9Q="; // Change this to a strong, secret key
    private static final long EXPIRATION_TIME = 864000000; // 10 days in milliseconds

    public String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}