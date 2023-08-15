
package com.wanted.global.security;

import com.wanted.global.redis.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String JWT_KEY;

    @Value("${jwt.expired}")
    private Long JWT_EXPIRED;

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(JWT_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getMemberId(String accessToken) {
        return extractAllClaims(accessToken).get("memberId", Long.class);
    }

    public String getRole(String accessToken) {
        return extractAllClaims(accessToken).get("role", String.class);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public AccessToken generateAccessToken(Long memberId, String role) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("role", role);

        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + JWT_EXPIRED);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(JWT_KEY), SignatureAlgorithm.HS256)
                .compact();

        return AccessToken.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .expire(JWT_EXPIRED)
                .expireTime(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}