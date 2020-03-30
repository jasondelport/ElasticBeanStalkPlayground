package com.jasondelport.demo.service;

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
public class AsyncMessageService {

	private static final Logger log = LoggerFactory.getLogger(AsyncMessageService.class);

	@Autowired
	private OutboundJMSService outboundJMSService;

	/*
	@Async must be applied to public methods only
 	self-invocation – calling the async method from within the same class – won’t work
 	*/
	@Async("threadPoolTaskExecutor")
	public Future<Boolean> sendMessage(String message, boolean debug) {
		if (debug) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				outboundJMSService.sendData(message);
			} catch (Exception ex) {
				log.error("ERROR: ", ex);
				return new AsyncResult<>(false);
			}
		}
		return new AsyncResult<>(true);
	}
}