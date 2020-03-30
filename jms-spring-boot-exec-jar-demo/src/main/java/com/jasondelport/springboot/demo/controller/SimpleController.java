package com.jasondelport.springboot.demo.controller;

import com.google.gson.JsonObject;
import com.jasondelport.springboot.demo.model.HelloWorld;
import com.jasondelport.springboot.demo.repository.DatabaseRepository;
import com.jasondelport.springboot.demo.service.SqsTestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class SimpleController {

	@Autowired
	private DatabaseRepository databaseRepository;

	@Autowired
	private SqsTestingService sqsTestingService;

	@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRoot() {
		JsonObject obj = databaseRepository.getJsonObject();
		return "\n" + obj.toString() + "\n";
	}

	@RequestMapping(path = "/obj", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloWorld> getRootObj() {
		return new ResponseEntity<>(databaseRepository.getObject(), HttpStatus.OK);
	}

	@RequestMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloWorld> sendMessage() {
		return new ResponseEntity<>(databaseRepository.sendMessage(), HttpStatus.OK);
	}

	@RequestMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public String runTests() {
		sqsTestingService.runTest();
		return  "\n" + "{ \"status\":\"started\" }" +  "\n";
	}

	@RequestMapping(path = "/throwerror", produces = MediaType.APPLICATION_JSON_VALUE)
	public String throwError() throws Exception {

		throw new Exception("There was a problem");
	}

}