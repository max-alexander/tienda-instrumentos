package com.example.postventa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Postventa API",
                description = "Microservicio de postventa de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
