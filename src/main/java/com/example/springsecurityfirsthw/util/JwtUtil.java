package com.example.springsecurityfirsthw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static String JWT_SECRET_KEY;

    @Value("${token.secret}")
    public void setJwtSecretKey(String JWT_SECRET_KEY) {
        JwtUtil.JWT_SECRET_KEY = JWT_SECRET_KEY;
    }


    private static long EXPIRATION_TIME;

    @Value("${token.timeLife}")
    public void setExpirationTime(Long EXPIRATION_TIME) {
        JwtUtil.EXPIRATION_TIME = EXPIRATION_TIME;
    }

    public static String generateToken(UserDetails userDetails) {
        // Логика генерации JWT
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        // Логика проверки JWT
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }
    public static String extractUsername(String token) {
        return extractAllClaimsForToken(token).getSubject();
    }

    public static Date extractExpiration(String token) {
        return extractAllClaimsForToken(token).getExpiration();
    }

    private static Claims extractAllClaimsForToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
