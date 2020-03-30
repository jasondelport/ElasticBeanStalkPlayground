package com.jasondelport.springboot.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public class AwsProperties {


	private String region;
	private String key;
	private String secret;
	private String table;
	private String queue;
	private String inboundQueue;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getInboundQueue() {
		return inboundQueue;
	}

	public void setInboundQueue(String inboundQueue) {
		this.inboundQueue = inboundQueue;
	}
}
