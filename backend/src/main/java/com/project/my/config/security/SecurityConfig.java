package com.project.my.config.security;

import com.java.project.api.config.jwt.JwtProvider;
import com.java.project.api.config.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;

    // 비밀번호 암호화 처리
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }


    //스프링부트 3.0에서 시큐리티 설정 방법이
    //configure 상속에서 SecurityFilterChain 으로 변경됨


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .anyRequest().permitAll()
                )

                .formLogin(form -> form.disable())

//                .cors(cors -> cors.configurationSource(new CorsConfig().corsConfiguration()))

                .httpBasic(auth -> auth.disable())

                .addFilterAt(
                        new LoginFilter(authenticationManager(authenticationConfiguration), jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
