package com.jasondelport.demo;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

	private SQSConnectionFactory connectionFactory;

	private static final Logger log = LoggerFactory.getLogger(JmsConfig.class);

	/*

	@Value("${aws.key}")
	private String key;

	@Value("${aws.secret}")
	private String secret;

	*/

	@Value("${aws.region}")
	private String region;

	@Value("${aws.outbound-queue}")
	private String outboundQueue;

	private SQSConnectionFactory getConnectionFactory() {

		/*
		AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(key, secret));
		*/

		if (connectionFactory == null) {
			AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
					.withRegion(region)
					.withCredentials(new DefaultAWSCredentialsProviderChain())
					//.withCredentials(awsStaticCredentialsProvider)
					.build();

			connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
		}
		return connectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(getConnectionFactory());
		factory.setDestinationResolver(new DynamicDestinationResolver());
		//start with 3 threads and scale up to 10.
		factory.setConcurrency("3-10");
		// Session.CLIENT_ACKNOWLEDGE will make Spring acknowledge (delete) the message after our
		// service method is complete. If the method throws an exception, Spring will recover the
		// message (that is, make it visible).
		/*
		With this acknowledgment mode, the client acknowledges a consumed message by calling the message's
		acknowledge method. Acknowledging a consumed message acknowledges all messages that the session
		has consumed. When client acknowledgment mode is used, a client may build up a large number of
		unacknowledged messages while attempting to process them. A JMS provider should provide administrators
		with a way to limit client overrun so that clients are not driven to resource exhaustion and ensuing
		failure when some resource they are using is temporarily blocked.
		 */
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		//factory.setMessageConverter(messageConverter());
		return factory;
	}

	@Bean
	public JmsTemplate defaultJmsTemplate() {

		JmsTemplate jmsTemplate = new JmsTemplate(getConnectionFactory());
		jmsTemplate.setDefaultDestinationName(outboundQueue);
		// Set whether message delivery should be persistent or non-persistent. default = true
		// messages are persisted to disk/database so that they will survive a broker restart
		// persistent messaging is usually slower than non-persistent delivery
		//jmsTemplate.setDeliveryPersistent(false);
		return jmsTemplate;
	}

	/*

	@Bean
    public MessageConverter messageConverter() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.dateFormat(new ISO8601DateFormat());

        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();

        mappingJackson2MessageConverter.setObjectMapper(builder.build());
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("documentType");
        return mappingJackson2MessageConverter;
    }

	 */

}
