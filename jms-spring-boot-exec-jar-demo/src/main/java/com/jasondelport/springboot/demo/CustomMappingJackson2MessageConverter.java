package com.jasondelport.springboot.demo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jasondelport.springboot.demo.model.Hello;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.Message;

public class CustomMappingJackson2MessageConverter extends MappingJackson2MessageConverter {

	// https://stackoverflow.com/questions/36447519/how-to-set-typeidpropertyname-in-mappingjackson2messageconverter

	@Override
	public JavaType getJavaTypeForMessage(Message message) {
		return TypeFactory.defaultInstance().constructType(Hello.class);
	}
}
