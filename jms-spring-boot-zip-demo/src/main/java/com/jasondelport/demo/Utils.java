package com.jasondelport.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonObject;

public class Utils {

	public static JsonNode gsonToJackson(JsonObject obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJsonString(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
