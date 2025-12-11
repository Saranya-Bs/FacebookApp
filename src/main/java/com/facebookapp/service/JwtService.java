package com.facebookapp.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.facebookapp.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long Expiration_time=60*60*1000;

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + Expiration_time)).
                signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token,UserDetails user){
        String tokenEmail=extractClaims(token).getSubject();
        if(!user.getUsername().equals(tokenEmail)){
            return false;
        }

        return !isTokenExpired(tokenEmail);
    }

    
}
