package com.example.capstone.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.sonatype.plexus.components.sec.dispatcher.model.ConfigProperty;
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
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedOrigins("http://localhost:8081")
                .allowedHeaders("Origin", "Accept", "X-Requested-With", "Content-Type", "Authorization", "Control-Request-Method", "Access-Control-Request-Headers" );
    }



}