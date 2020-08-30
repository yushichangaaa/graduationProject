package com.ysc;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableEurekaClient
public class MasterApp {
	public static void main(String[] args) {
		SpringApplication.run(MasterApp.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("E:\\graduationProject\\parent\\master\\src\\main\\resources");//指定临时文件路径，这个路径可以随便写
		return factory.createMultipartConfig();
	}
}
