package com.jasondelport.springboot.demo.model;

import java.io.Serializable;

public class Hello implements Serializable {

	private String message;

	public Hello() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
