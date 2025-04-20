package com.project.my.auth.controller;

import com.project.my.config.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
public class JWTController {

    private final JwtProvider jwtProvider;
    private final String ACCESS_TYPE = "access";
    private final String REFRESH_TYPE = "refresh";
    private final String TOKEN_PREFIX = "Bearer ";

    // TODO : access 만료 시 refresh 를 이용해서 재발급 해야함.
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        String header = request.getHeader("refresh");

        for (Cookie cookie : cookies) {
            log.info(cookie.getName());

            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh is null", HttpStatus.BAD_REQUEST);
        }

//        String type = jwtProvider.getRole(refresh);
//
//        if (!type.equals(REFRESH_TYPE)) {
//            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
//        }

        String username = jwtProvider.getUserId(refresh);
        String role = jwtProvider.getRole(refresh);

        String newAccess = jwtProvider.createJwt(ACCESS_TYPE, username, role,
            1000L * 60 * 60 * 24 * 10);
        String newRefresh = jwtProvider.createJwt(REFRESH_TYPE, username, role,
            1000L * 60 * 60 * 24 * 10);

        response.setHeader("Authorization", "Bearer " + newAccess);
        response.addCookie(createCookie(REFRESH_TYPE, newRefresh));

        return ResponseEntity.ok().build();
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
