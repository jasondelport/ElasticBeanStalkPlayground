package com.jasondelport.demo.controller;

import com.jasondelport.demo.model.Config;
import com.jasondelport.demo.service.AsyncSqsTestingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SimpleController {

	private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

	@Autowired
	private AsyncSqsTestingService asyncSqsTestingService;

	@RequestMapping(path = "/", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity startTesting(@RequestBody Config config) {

		log.info("\n" + config.toJsonString());

		if (config.getNumberOfMessages() > 0 && config.getTimeInMinutes() > 0
				&& config.getDriverId() != null && config.getRouteId() != null) {
			try {
				// this is async
				asyncSqsTestingService.runTest(config);
			} catch (Exception e) {
				log.error("error: " + e);
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(HttpStatus.OK);
		}
		//ResponseEntity(T body, MultiValueMap<String,String> headers, HttpStatus statusCode)
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "/crontest", method = RequestMethod.POST)
	public ResponseEntity cronTest() {
		log.info("running cron");
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String getRoot() {
		return "OK";
	}
}