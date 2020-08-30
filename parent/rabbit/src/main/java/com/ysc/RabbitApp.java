package com.ysc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RabbitApp {

	public static void main(String[] args) {
		SpringApplication.run(RabbitApp.class, args);
	}
}
