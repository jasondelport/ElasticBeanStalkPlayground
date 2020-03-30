package com.jasondelport.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jasondelport.demo.model.Config;
import com.jasondelport.demo.model.OutboundMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@Service
public class AsyncSqsTestingService {

	private static final Logger log = LoggerFactory.getLogger(AsyncSqsTestingService.class);

	@Autowired
	private AsyncMessageService asyncMessageService;

	public AsyncSqsTestingService() {
	}

	@Async("threadPoolTaskExecutor")
	public void runTest(Config config) {

		Gson gson = new GsonBuilder().create();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'", Locale.UK);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

		List<OutboundMessage> queue = new ArrayList<>();

		int timeInMillis = config.getTimeInMinutes() * 60 * 1000;
		int numberOfMessages = config.getNumberOfMessages();
		if (numberOfMessages < 4) {
			numberOfMessages = 4;
		}

		int loginMessages = numberOfMessages / 4;
		int vehicleBreakDOwnMessages = numberOfMessages / 4;
		int locationUpdateMessages = numberOfMessages / 4;
		int deliveryCompletedMessages = numberOfMessages / 4;

		// login
		for (int i = 0; i < loginMessages; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId(config.getDriverId());
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName(config.getRouteId());
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(0);
			outboundMessage.setType("KT04");
			JsonObject object = new JsonObject();
			object.addProperty("subType", "login");
			outboundMessage.setData(object);
			if (config.isDebug() && i == 0) {
				log.info("\n" + outboundMessage.toString());
			}
			queue.add(outboundMessage);
		}

		// vehicle breakdown
		for (int i = 0; i < vehicleBreakDOwnMessages; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId(config.getDriverId());
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName(config.getRouteId());
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(8);
			outboundMessage.setType("KT08");
			JsonObject object = new JsonObject();
			object.addProperty("subType", "Vehicle Breakdown");
			outboundMessage.setData(object);
			if (config.isDebug() && i == 0) {
				log.info("\n" + outboundMessage.toString());
			}
			queue.add(outboundMessage);
		}

		// location update
		for (int i = 0; i < locationUpdateMessages; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId(config.getDriverId());
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName(config.getRouteId());
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(10);
			outboundMessage.setType("KT05");
			//outboundMessage.setData(new JsonObject());
			if (config.isDebug() && i == 0) {
				log.info("\n" + outboundMessage.toString());
			}
			queue.add(outboundMessage);
		}

		// delivery completed
		for (int i = 0; i < deliveryCompletedMessages; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId(config.getDriverId());
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName(config.getRouteId());
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(10);
			outboundMessage.setType("KT04");
			JsonObject object = new JsonObject();
			object.addProperty("subType", "delivery");
			object.addProperty("dropId", "1590715");
			object.addProperty("addressId", "1570503");
			object.addProperty("deliveryNumber", 1);
			object.addProperty("deliveryDescription", "");
			object.addProperty("deliveryStartTimestamp", dateFormatGmt.format(new Date()));
			object.addProperty("deliveryEndTimestamp", dateFormatGmt.format(new Date()));
			object.addProperty("deliveryCode", "D");
			object.addProperty("cashCollected", false);
			object.addProperty("receivedPaymentAuthorisation", false);
			object.addProperty("shortages", 5);
			object.addProperty("returnedItems", 5);
			object.addProperty("cagesLeft", 5);
			object.addProperty("cagesCollected", 5);
			object.addProperty("palletsLeft", 5);
			object.addProperty("palletsCollected", 5);
			object.addProperty("dropUnchecked", false);
			object.addProperty("instructions", "");
			object.addProperty("contactCustomer", false);
			outboundMessage.setData(object);
			if (config.isDebug() && i == 0) {
				log.info("\n" + outboundMessage.toString());
			}
			queue.add(outboundMessage);
		}

		Collections.shuffle(queue);

		int delayInMillis = timeInMillis / numberOfMessages - 5; // time divided by number of messages
		log.info("messages in queue: " + queue.size());
		log.info("length of test: " + timeInMillis / 1000 / 60 + " minutes");
		log.info("delay between message sending: " + delayInMillis + " millis");
		log.info("start sending: " + new Date().toString());
		for (OutboundMessage message : queue) {
			// this is async
			asyncMessageService.sendMessage(gson.toJson(message), config.isDebug());
			try {
				Thread.sleep(delayInMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("end sending: " + new Date().toString());

	}

}