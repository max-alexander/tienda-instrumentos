package com.example.compra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Compra API",
                description = "Microservicio de compras de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
