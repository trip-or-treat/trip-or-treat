package com.triportreat.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("https://localhost:3000")
                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("https://localhost:8080")
                .allowedOrigins("http://triportreat.site")
                .allowedOrigins("https://triportreat.site")
                .allowedOrigins("http://www.triportreat.site")
                .allowedOrigins("https://www.triportreat.site")
                .allowedOrigins("https://test-cors.org")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
