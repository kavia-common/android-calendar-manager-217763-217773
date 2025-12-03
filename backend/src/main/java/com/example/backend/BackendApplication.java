package com.example.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PUBLIC_INTERFACE
 * BackendApplication is the entry point for the Spring Boot application.
 * It sets up component scanning for the com.example.backend package and
 * provides OpenAPI metadata for Swagger UI documentation.
 */
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Calendar Backend API",
                version = "0.1.0",
                description = "REST service for managing calendar events"
        )
)
public class BackendApplication {

    /**
     * PUBLIC_INTERFACE
     * Application entrypoint.
     * @param args CLI args
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
