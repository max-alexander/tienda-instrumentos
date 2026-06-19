package com.example.despacho.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Despacho API",
                description = "Microservicio de despacho de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
