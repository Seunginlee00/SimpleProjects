//package com.project.my.config.jwt;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.my.common.dto.login.LoginRequest;
//import com.project.my.common.dto.response.JwtResponse;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Iterator;
//
//@RequiredArgsConstructor
//@Slf4j
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtProvider jwtProvider;
//
//    @Override
//    public Authentication attemptAuthentication(
//            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//      LoginRequest loginRequest = null;
//      try {
//        loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//
//      String username = loginRequest.username();
//      String password = loginRequest.password();
//
//        log.debug("username: {}", username);
//        log.debug("password: {}", password);
//
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                username, password, null);
//        return authenticationManager.authenticate(token);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request,
//                                              HttpServletResponse response, AuthenticationException failed)
//            throws IOException{
//
//        response.setStatus(401);
//
//        log.debug("들어옴");
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(
//                "{\"result\" : \"fail\", "
//                        + "\"errorCode\" : \"E0000\", "
//                        + "\"message\" : \"아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.\""
//                        + "}"
//        );
//
//    }
//
//
//
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response, FilterChain chain, Authentication authentication)
//            throws IOException {
//
//
//        // role 뽑기
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//
//        String role = auth.getAuthority();
//
//
//        log.info("role={}" + role);
//        String id = "";
//        CustomUsersDetails customAdminDetails = (CustomUsersDetails) authentication.getPrincipal();
//        id = customAdminDetails.getUsername();
//
//        String accessToken = jwtProvider.createJwt("access", id, role,
//                1000L * 60 * 60 * 24 * 10);  // 밀리세컨 * 초 * 분 * 시간
//
//        String refreshToken = jwtProvider.createJwt("refresh", id, role,
//                1000L * 60 * 60 * 24 * 10);  // 밀리세컨 * 초 * 분 * 시간 * 일
//
//
//        response.setHeader("Authorization", "Bearer " + accessToken);
//        response.addCookie(createCookie("refresh", refreshToken));
//        response.setStatus(HttpStatus.OK.value());
//
//        JwtResponse jwtResponse = new JwtResponse("success",
//                "로그인 성공하였습니다.",
//                false, accessToken,
//                refreshToken);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String result = objectMapper.writeValueAsString(jwtResponse);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(result);
//
//        // 로그인 기록 저장 을 추후 추가 해도 됨
//
//    }
//
//
//    private Cookie createCookie(String key, String value) {
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setAttribute("SameSite", "None");
//
//        return cookie;
//    }
//
//}
