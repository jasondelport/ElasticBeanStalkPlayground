package com.jasondelport.springboot.demo.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.gson.JsonObject;
import com.jasondelport.springboot.demo.component.NotificationComponent;
import com.jasondelport.springboot.demo.config.AwsProperties;
import com.jasondelport.springboot.demo.model.Demo;
import com.jasondelport.springboot.demo.model.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Repository
public class DatabaseRepository {

	private static final Logger log = LoggerFactory.getLogger(DatabaseRepository.class);

	@Value("${message}")
	private String message;

	@Autowired
	private AwsProperties props;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private NotificationComponent notification;

	public JsonObject getJsonObject() {

		String dbMessage = null;

		try {

			DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
			Table table = dynamoDB.getTable(props.getTable());
			Index index = table.getIndex("timestamp-index");

			DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);

			Map<String, String> nameMap1 = new HashMap<>();
			nameMap1.put("#ts", "timestamp");

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			long ts = calendar.getTimeInMillis();

			Map<String, AttributeValue> eav = new HashMap<>();
			eav.put(":ts", new AttributeValue().withN(String.valueOf(ts)));

			DynamoDBQueryExpression<Demo> queryExpression = new DynamoDBQueryExpression<Demo>()
					.withKeyConditionExpression("#ts < :ts")
					.withExpressionAttributeValues(eav)
					.withExpressionAttributeNames(nameMap1);


			/*
			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
			DynamoDB dynamo = new DynamoDB(client);
			Table tb = dynamo.getTable("people");

			// PrimaryKey(String hashKeyName, Object hashKeyValue, String rangeKeyName, Object rangeKeyValue)
			// withPrimaryKey(String hashKeyName, Object hashKeyValue, String rangeKeyName, Object rangeKeyValue)

			*/


			String json = "{ \"message\":\"hello\" }";

			PrimaryKey pk = new PrimaryKey("timestamp",System.currentTimeMillis(), "type", "sqs");

			Item item =
					new Item()
							.withPrimaryKey(pk)
							.with("message", "hello json")
							.with("id", "id9898")
							.with("extra", "extra data")
							.withJSON("json", json);

			table.putItem(item);

			Demo d = new Demo();
			d.setType("sqs");

			DynamoDBQueryExpression<Demo> queryExpression1 = new DynamoDBQueryExpression<Demo>()
					.withHashKeyValues(d)
					.withScanIndexForward(false);

			List<Demo> list1 = mapper.query(Demo.class, queryExpression1);

			log.info("list1 size -> {}", list1.size());

			for (Demo demo : list1) {
				log.info(demo.getMessage());
				dbMessage = demo.getMessage();
			}

			/*

			DynamoDBScanExpression scanExpression2 = new DynamoDBScanExpression()
					.withIndexName("timestamp-index")
					.withFilterExpression(" #ts > :ts")
					.withExpressionAttributeValues(eav)
					.withExpressionAttributeNames(nameMap1);

			List<Demo> list = mapper.scan(Demo.class, scanExpression2);

			log.info("list size -> {}", list.size());

			for (Demo demo : list) {
				log.info(demo.getMessage());
			}

			if (!list.isEmpty()) {
				message = list.get(list.size() - 1).getMessage();
			}

			Map<String, String> nameMap = new HashMap<>();
			nameMap.put("#ty", "type");
			nameMap.put("#ts", "timestamp");
			nameMap.put("#m", "message");

			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(":ty", "sqs");
			valueMap.put(":ts", ts);
			valueMap.put(":m", "Hello");

			QuerySpec querySpec = new QuerySpec()
					.withKeyConditionExpression("#ty = :ty and #ts > :ts")
					.withFilterExpression("contains (#m, :m)")
					.withNameMap(nameMap)
					.withValueMap(valueMap);

			ItemCollection<QueryOutcome> items = table.query(querySpec);

			Iterator<Item> iterator = items.iterator();
			while (iterator.hasNext()) {
				log.info(iterator.next().toJSONPretty());
			}

			*/

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		JsonObject obj = new JsonObject();
		if (dbMessage != null) {
			obj.addProperty("message", dbMessage);
		} else {
			obj.addProperty("message", "Hello world!!");
		}
		return obj;
	}

	public HelloWorld getObject() {
		return new HelloWorld("Hello world!", System.currentTimeMillis());
	}

	public HelloWorld sendMessage() {
		try {
			String newJson = "Sent from Controller";
			notification.sendData(newJson);
		} catch (IOException ex) {

		}
		return new HelloWorld("Message sent", System.currentTimeMillis());
	}
}