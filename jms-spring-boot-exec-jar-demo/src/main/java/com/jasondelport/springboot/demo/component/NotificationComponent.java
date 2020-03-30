package com.jasondelport.springboot.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NotificationComponent {

	private static final Logger log = LoggerFactory.getLogger(NotificationComponent.class);

	@Autowired
	private JmsTemplate defaultJmsTemplate;

	public void sendData(String json) throws IOException {
		defaultJmsTemplate.convertAndSend(json);
	}

}