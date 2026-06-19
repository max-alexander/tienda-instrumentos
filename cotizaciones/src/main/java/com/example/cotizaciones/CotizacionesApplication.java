package com.example.cotizaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CotizacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CotizacionesApplication.class, args);
	}

}
