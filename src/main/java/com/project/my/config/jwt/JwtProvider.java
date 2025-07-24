package com.project.my.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
https://ng-log.tistory.com/entry/SpringBoot-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0-4-JWT-%ED%86%A0%ED%81%B0-%EC%83%9D%EC%84%B1%ED%95%98%EA%B8%B0?category=1107954#JwtTokenProvider
https://github.com/globepoint-github/smart-taekwondo/blob/back_dev/backend/src/main/java/com/smarttaekwondo/globepoint/config/jwt/JWTUtil.java

* */

@Slf4j
@Component
public class JwtProvider {

    private static String key = "1234567890123456789012345678901234567890";


    private SecretKey secretKey;

    public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build()
                        .getAlgorithm());
    }

    /* 회원 아이디 가져오기 */
    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("userId", String.class);
    }

    /*회원 권한 가져오기*/
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("role", String.class);
    }
    /*토큰 만료여부*/
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }
    /* access 인지 refresh 인지 구분*/
    public String getType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("type", String.class);
    }

    /* 토큰 생성 */
    public String createJwt(String type, String userId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("type", type)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    /*토큰 추출 */

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {

            if (e instanceof SecurityException) {
                log.debug("[SecurityException] 잘못된 토큰");
                throw new JwtException("[SecurityException] 잘못된 토큰입니다.");
            } else if (e instanceof MalformedJwtException) {
                log.debug("[MalformedJwtException] 잘못된 토큰");
                throw new JwtException("[MalformedJwtException] 잘못된 토큰입니다.");
            } else if (e instanceof ExpiredJwtException) {
                log.debug("[ExpiredJwtException] 토큰 만료");
                throw new JwtException("[ExpiredJwtException] 토큰 만료");
            } else if (e instanceof UnsupportedJwtException) {
                log.debug("[UnsupportedJwtException] 잘못된 형식의 토큰");
                throw new JwtException("[UnsupportedJwtException] 잘못된 형식의 토큰");
            } else if (e instanceof IllegalArgumentException) {
                log.debug("[IllegalArgumentException]");
                throw new JwtException("[IllegalArgumentException]");
            } else {
                log.debug("[토큰검증 오류]" + e.getClass());
                throw new JwtException("[토큰검증 오류] 미처리 토큰 오류");
            }
        }
    }

    public static Map<String, Object> validateToken2(String token) {

        SecretKey key = null;

        try {
            try {
                key = Keys.hmacShaKeyFor(JwtProvider.key.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            log.info("claims: " + claims);

            return claims;
        } catch (ExpiredJwtException e) {
            log.warn("Token is expired", e);
            throw e;
        } catch (JwtException e) {
            log.error("Invalid JWT detected", e);
            throw e;
        }
    }


}

