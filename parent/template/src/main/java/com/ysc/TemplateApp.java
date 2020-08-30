package com.ysc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class TemplateApp {

	public static void main(String[] args) {
		
		SpringApplication.run(TemplateApp.class, args);
	}
	
	@Bean
	public RestTemplate getRestTempalte() {
		return new RestTemplate();
	}
}
