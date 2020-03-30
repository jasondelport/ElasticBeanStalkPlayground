package com.jasondelport.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InboundJMSService {

	private static final Logger log = LoggerFactory.getLogger(InboundJMSService.class);

	/*

	// in case the built in queue reader mechanism doesn't work

	@Autowired
	private AsyncSqsTestingService sqsTestingService;

	// implicit message type conversion
	// public void handleMessage(@Payload Config config, MessageHeaders messageHeaders) throws JMSException {
	@JmsListener(destination = "${aws.inbound-queue}")
	public void handleMessage(String message) throws JMSException {
		log.info("raw message -> " + message);
		Config config = new Gson().fromJson(message, Config.class);
		log.info("\n" + config.toJsonString());

		if (config.getNumberOfMessages() > 0 && config.getTimeInMinutes() > 0
				&& config.getDriverId() != null && config.getRouteId() != null) {
			try {
				// this is async
				sqsTestingService.runTest(config);
			} catch (Exception e) {
				log.error("error: " + e);
			}
		}

	}

	*/


	/*

	@SqsListener(value = "test-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void queueListener(String msg, Acknowledgment acknowledgment){
		System.out.println("message: " + msg);

		if(true){
			acknowledgment.acknowledge();
		}
	}

	*/



}