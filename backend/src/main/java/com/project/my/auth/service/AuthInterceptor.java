package com.project.my.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttaekwondo.globepoint.auth.service.dto.JWTException;
import com.smarttaekwondo.globepoint.config.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;
    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authorization.split(" ")[1];

        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            JWTException jwtException = new JWTException(true, "토큰이 만료 되었습니다.");
            String result = objectMapper.writeValueAsString(jwtException);
            response.getWriter().write(result);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String type = jwtUtil.getType(token);

        if (!type.equals("access")) {
            JWTException jwtException = new JWTException(true, "Invalid Access Token");
            String result = objectMapper.writeValueAsString(jwtException);
            response.getWriter().write(result);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }

        String tokenGetMemberId = jwtUtil.getMemberId(token);
        Long memberId = Long.valueOf(tokenGetMemberId);
        authContext.setMemberId(memberId);

        return true;
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");

        return cookie;
    }
}
