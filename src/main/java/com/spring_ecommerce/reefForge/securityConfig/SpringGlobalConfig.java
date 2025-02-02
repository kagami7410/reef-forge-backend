package com.spring_ecommerce.reefForge.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("https://reef-forge-backend-service.reef-forge-backend.svc.cluster.local")
//                .allowedOrigins("http://reef-forge-backend-service.reef-forge-backend.svc.cluster.local")
//                .allowedOrigins("https://reef-forge-frontend-service.reef-forge-frontend.svc.cluster.local")
//                .allowedOrigins("http://reef-forge-frontend-service.reef-forge-frontend.svc.cluster.local")

                .allowedOrigins("*")
//                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*");
    }
}