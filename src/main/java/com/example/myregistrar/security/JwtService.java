package com.example.myregistrar.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class JwtService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExp}")
    private long jwtExp;

    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExp))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8)))
                .compact();
    }

    public boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8)))
                    .build().parseClaimsJwt(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8)))
                .build().parseClaimsJwt(jwt)
                .getBody().getSubject();
    }

    public String getRoleFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8)))
                .build().parseClaimsJwt(jwt)
                .getBody().get("role", String.class);
    }
}
