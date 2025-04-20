package com.project.my.auth.config;


import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthArgumentResolver authArgumentResolver;


    // 인터셉터를 태울 곳
    private final List<String> addEndPoint = Arrays.asList(
        "/**"
    );

    // 인터셉터를 안태울 곳
    private final List<String> excludePoint = Arrays.asList(
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/login",
        "/reissue"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns(addEndPoint)
            .excludePathPatterns(excludePoint);

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }


}
