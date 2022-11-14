package com.fmatheus.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.CircuitBreaker;

@CircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class CambiumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CambiumServiceApplication.class, args);
	}

}
