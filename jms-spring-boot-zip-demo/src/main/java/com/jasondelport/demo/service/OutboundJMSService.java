package com.jasondelport.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OutboundJMSService {

	private static final Logger log = LoggerFactory.getLogger(OutboundJMSService.class);

	@Autowired
	private JmsTemplate defaultJmsTemplate;

	public void sendData(String json) throws Exception {
		defaultJmsTemplate.convertAndSend(json);
	}

}