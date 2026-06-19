package com.example.reserva.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Reserva API",
                description = "Microservicio de reservas de instrumentos musicales",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
