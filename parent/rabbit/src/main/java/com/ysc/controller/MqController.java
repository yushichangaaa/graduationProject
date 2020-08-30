package com.ysc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.config.RabbitConfig;

@RestController
public class MqController {

	public static Logger log = LoggerFactory.getLogger(MqController.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@RequestMapping(value = "/send",method = {RequestMethod.GET , RequestMethod.POST},produces = {"application/json;charset=utf-8"})
	public void sendMsgToRabbit() {
		log.info("已经发送消息");
		this.amqpTemplate.convertAndSend(RabbitConfig.QUEUE, 0);
		
	}
}
