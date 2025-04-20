package com.project.my.config;

import com.project.my.config.annotation.UserIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserIdInterceptor())  // 인터셉터 등록
                .addPathPatterns("/**");  // 모든 경로에 대해 인터셉터 적용
    }
}
