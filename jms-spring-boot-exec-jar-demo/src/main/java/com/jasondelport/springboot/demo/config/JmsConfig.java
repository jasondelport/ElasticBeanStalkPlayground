package com.jasondelport.springboot.demo.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasondelport.springboot.demo.CustomMappingJackson2MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

	private SQSConnectionFactory connectionFactory;
	private AmazonSQS amazonSQS;

	@Autowired
	private AwsProperties props;

	private static final Logger log = LoggerFactory.getLogger(JmsConfig.class);

	@Bean
	public AmazonSQS getAmazonSQS() {
		if (amazonSQS == null) {
			amazonSQS = AmazonSQSClientBuilder.standard()
					.withRegion(props.getRegion())
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(props.getKey(), props.getSecret())))

								.build();
		}
		return amazonSQS;
	}

	@Bean
	public SQSConnectionFactory getConnectionFactory() {
		if (connectionFactory == null) {
			connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), getAmazonSQS());
		}
		return connectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		FixedBackOff fixedBackOff = new FixedBackOff();
		fixedBackOff.setMaxAttempts(10);
		fixedBackOff.setInterval(5000);

		ExponentialBackOff exponentialBackOff = new ExponentialBackOff();
		exponentialBackOff.setInitialInterval(1000L);
		exponentialBackOff.setMultiplier(3);
		exponentialBackOff.setMaxElapsedTime(1000*60*5);

		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(getConnectionFactory());
		factory.setDestinationResolver(new DynamicDestinationResolver());
		factory.setConcurrency("3-10");
		factory.setBackOff(fixedBackOff);
		//factory.setSessionTransacted(true);
		//factory.setRecoveryInterval(5000L);
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		factory.setErrorHandler(t -> log.error(t.getMessage()));
		factory.setMessageConverter(new CustomMappingJackson2MessageConverter());
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		return converter;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	@Bean
	public MessageConverter messageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper());
		return converter;
	}

	@Bean
	public JmsTemplate defaultJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(getConnectionFactory());
		jmsTemplate.setDefaultDestinationName(props.getInboundQueue());
		return jmsTemplate;
	}

	/*
	@Bean
	public MessageConverter messageConverter() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);

		MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
		mappingJackson2MessageConverter.setObjectMapper(builder.build());
		mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
		mappingJackson2MessageConverter.setTypeIdPropertyName("documentType");
		return mappingJackson2MessageConverter;
	}
	*/

}
