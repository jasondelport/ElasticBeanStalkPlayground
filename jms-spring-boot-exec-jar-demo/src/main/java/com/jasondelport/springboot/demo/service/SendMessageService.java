package com.jasondelport.springboot.demo.service;

import com.jasondelport.springboot.demo.config.DbConfig;
import com.jasondelport.springboot.demo.component.NotificationComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Configuration
@Service
public class SendMessageService {

	private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

	@Autowired
	private NotificationComponent notification;


	/*

	@Async must be applied to public methods only
 	self-invocation – calling the async method from within the same class – won’t work

	https://www.baeldung.com/spring-async
 	*/
	@Async("threadPoolTaskExecutor")
	public Future<String> sendMessage(String message) {
		try {
			Thread.sleep(500);
			notification.sendData(message);
		} catch (Exception ex) {
			log.error("Encountered error while parsing message.", ex);
			//throw new JMSException("Encountered error while parsing message.");
		}
		return new AsyncResult<>("completed");
	}
}