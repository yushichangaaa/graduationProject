package com.ysc.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ScheduleConfig {

	@Bean(name = "ScheduleFactory")
	@Primary
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setQuartzProperties(quartzProperties());
		return factory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		
		propertiesFactoryBean.afterPropertiesSet();
		
		return propertiesFactoryBean.getObject();
	}
	
	@Bean
	public QuartzInitializerListener executorListener() {
		return new QuartzInitializerListener();
	}
	
	@Bean(name = "Scheduler")
	public Scheduler scheduler() throws IOException {
		return schedulerFactoryBean().getScheduler();
	}
	
}
