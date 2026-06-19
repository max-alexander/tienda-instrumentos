package com.example.pago.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pago API",
                description = "Microservicio de pagos de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
