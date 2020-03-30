package com.jasondelport.springboot.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@DynamoDBTable(tableName = "Demo")
public class Demo {

	private String id;
	private String type;
	private long timestamp;
	private String message;
	private String extra;

	// Partition key – A simple primary key, composed of one attribute known as the partition key.
	// DynamoDB uses the partition key's value as input to an internal hash function.
	// The output from the hash function determines the partition (physical storage
	// internal to DynamoDB) in which the item will be stored.
	@DynamoDBHashKey(attributeName = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// The sort key of an item is also known as its range attribute. The term range
	// attribute derives from the way DynamoDB stores items with the same partition
	// key physically close together, in sorted order by the sort key value.
	@DynamoDBRangeKey(attributeName = "timestamp")
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long rangeKey) {
		this.timestamp = timestamp;
	}

	@DynamoDBAttribute(attributeName = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@DynamoDBAttribute(attributeName = "extra")
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String toJsonString() {
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		Gson gson = gb.create();
		return gson.toJson(this);
	}

}
