package com.example.proyecto_resena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProyectoResenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoResenaApplication.class, args);
	}

}
