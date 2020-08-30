package com.ysc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScheduleApp {
	public static void main(String[] args) {
		SpringApplication.run(ScheduleApp.class, args);
	}
}
