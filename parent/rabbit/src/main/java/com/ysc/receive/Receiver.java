package com.ysc.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ysc.config.RabbitConfig;
import com.ysc.service.PeopleFlowService;

@Component
public class Receiver {

	public static Logger log = LoggerFactory.getLogger(Receiver.class);
	
	@Autowired
	PeopleFlowService service;
	
	
	@RabbitListener(queues = RabbitConfig.QUEUE)
	public void receiver(int i) {
		boolean b = service.addFlow();
		if(b) {
			log.info("rabbit成功");
			
		} else {
			log.info("rabbit失败");
		}
	}
}
