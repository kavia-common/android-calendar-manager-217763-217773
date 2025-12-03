package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * CorsConfig enables CORS for Android emulator and local development.
 * Allows origins:
 *  - http://10.0.2.2:3001
 *  - http://10.0.2.2:3000
 *  - http://localhost:3000
 *  - http://localhost:3001
 */
@Configuration
public class CorsConfig {

    /**
     * PUBLIC_INTERFACE
     * Registers a CorsFilter bean covering all paths.
     * @return CorsFilter configured for allowed origins, headers, and methods.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(
                "http://10.0.2.2:3000",
                "http://10.0.2.2:3001",
                "http://localhost:3000",
                "http://localhost:3001"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Location"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
