package com.study.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 허용할 도메인 주소
                .allowedOrigins("http://localhost:3000")
                // 허용할 HTTP 메서드
                .allowedMethods("*")
                // 쿠키를 사용하는 경우 true로 설정
                .allowCredentials(true);
    }


}