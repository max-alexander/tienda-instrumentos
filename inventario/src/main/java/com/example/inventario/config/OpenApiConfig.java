package com.example.inventario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Inventario API",
                description = "Microservicio de inventario de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
