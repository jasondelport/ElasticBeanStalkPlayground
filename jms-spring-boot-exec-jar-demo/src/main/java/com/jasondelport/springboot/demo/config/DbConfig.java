package com.jasondelport.springboot.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

	private AmazonDynamoDB ddb;

	@Autowired
	private AwsProperties props;

	private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		if (ddb == null) {
			ddb = AmazonDynamoDBClientBuilder.standard()
					.withRegion(props.getRegion())
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(props.getKey(), props.getSecret())))
					.build();
		}
		return ddb;
	}

}
