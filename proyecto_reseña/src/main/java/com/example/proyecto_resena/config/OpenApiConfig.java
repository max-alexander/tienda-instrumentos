package com.example.proyecto_resena.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Proyecto Reseña API",
                description = "Microservicio de reseñas de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
