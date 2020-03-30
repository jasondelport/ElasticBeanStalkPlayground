package com.jasondelport.springboot.demo.service;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.sqs.AmazonSQS;
import com.jasondelport.springboot.demo.config.AwsProperties;
import com.jasondelport.springboot.demo.model.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
public class SQSService {

	private static final Logger log = LoggerFactory.getLogger(SQSService.class);


	/*


	Amazon SQS has no transaction support, so messages might therefore be retrieved twice.
	Application have to be written in an idempotent way so that they can receive a message twice.


	Annotated JMS listener methods are allowed to have flexible signatures similar to what MessageMapping provides:

	Session to get access to the JMS session
	Message or one of its subclasses to get access to the raw JMS message
	Message to use Spring's messaging abstraction counterpart
	@Payload-annotated method arguments, including support for validation
	@Header-annotated method arguments to extract specific header values, including standard JMS headers defined by JmsHeaders
	@Headers-annotated method argument that must also be assignable to Map for obtaining access to all headers
	MessageHeaders arguments for obtaining access to all headers
	MessageHeaderAccessor or JmsMessageHeaderAccessor for convenient access to all method arguments
	Annotated methods may have a non-void return type. When they do, the result of the method
	invocation is sent as a JMS reply to the destination defined by the JMSReplyTO header of the
	incoming message. If this header is not set, a default destination can be provided by
	adding @SendTo to the method declaration.

	 */


	@Autowired
	private AwsProperties props;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private AmazonSQS amazonSQS;

	/*

	{
		"message" : "hello there"
	}

	 */

	//@JmsListener(destination = "${aws.queue}")
	//public void handleMessage(@Payload String payload, Message message, MessageHeaders messageHeaders) throws JMSException {


	// https://cloud.spring.io/spring-cloud-aws/1.2.x/

	//@SqsListener("${aws.queue}")
	// see CustomMappingJackson2MessageConverter class to allow this conversion
	// the message could contain a property
	// https://stackoverflow.com/questions/36447519/how-to-set-typeidpropertyname-in-mappingjackson2messageconverter
	@JmsListener(destination = "${aws.queue}")
	public void handleMessage(Hello hello, SQSTextMessage message, MessageHeaders messageHeaders) throws JMSException {

		log.info("{}",hello.getMessage());
		log.info("exp {}",message.getIntProperty("JMSXDeliveryCount"));
		log.info("{}",messageHeaders.toString());

		//SQSTextMessage sqsTextMessage = (SQSTextMessage) message;

		//String receipt = sqsTextMessage.getReceiptHandle();
		//String queueUrl = sqsTextMessage.getQueueUrl();

		//log.info("{} {}",receipt, queueUrl);

		//log.info("headers -> {}", messageHeaders.toString());

		/*
		Hello hello = null;
		try {
			hello = new ObjectMapper().readValue(payload, Hello.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		//Hello hello = message.getPayload();
		//log.info("payload -> {}", hello);


		//log.info("Received message -> " + sqsTextMessage.getText());
		//log.info("AcknowledgeMode -> " + session.getAcknowledgeMode());

		//String val = hello.getMessage();

		/*

		HashMap<String, AttributeValue> fields = new HashMap<>();
		fields.put("type", new AttributeValue("sqs"));
		fields.put("id", new AttributeValue(UUID.randomUUID().toString()));
		fields.put("timestamp", new AttributeValue().withN(String.valueOf(System.currentTimeMillis())));
		fields.put("message", new AttributeValue(val));

		try {
			amazonDynamoDB.putItem(props.getTable(), fields);
		} catch (Exception e) {
			log.error("error: " + e);
		}

		*/


		//   ChangeMessageVisibilityResult changeMessageVisibility(String var1, String var2, Integer var3);
		//amazonSQS.changeMessageVisibility(queueUrl, receipt, 5000);
		//log.info("result -> " + result.toString());

		//throw new JMSException("Oh oh");

	}

	/*

	@SqsListener(value = "${aws.queue}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void queueListener(String message, Acknowledgment acknowledgment){
		System.out.println("message: " + message);

			//acknowledgment.acknowledge();

	}

	*/



}