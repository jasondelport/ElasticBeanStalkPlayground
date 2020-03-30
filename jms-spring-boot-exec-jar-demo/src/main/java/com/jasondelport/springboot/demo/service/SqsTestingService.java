package com.jasondelport.springboot.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jasondelport.springboot.demo.model.OutboundMessage;
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
public class SqsTestingService {

	private static final Logger log = LoggerFactory.getLogger(SqsTestingService.class);

	@Autowired
	private SendMessageService sendMessageService;

	public SqsTestingService() {
	}

	public static void main(String[] args) {
		SqsTestingService sqsTestingService = new SqsTestingService();
		sqsTestingService.runTest();

	}

	@Async("threadPoolTaskExecutor")
	public void runTest() {
		/*

		UNKNOWN(0),
		ACCEPTED(5),
		DISPUTED(6),
		ALLOCATED(7),
		LATE_DEPARTURE(8),
		VEHICLE_CHECKS(9),
		IN_TRANSIT(10),
		DELIVERING(11),
		ON_BREAK(12),
		DELIVERIES_COMPLETED(13),
		RETURNED_TO_DEPOT(14),
		DE_KIT(15),
		DE_BRIEF(16),
		ROUTE_COMPLETED(17),
		VEHICLE_ACCIDENT(18),
		PEOPLE_ACCIDENT(19),
		VEHICLE_BREAKDOWN(20),
		ABORTED(21),
		AT_RISK(22),
		EMPLOYEE_ACCIDENT(23),
		ENFORCEMENT_STOP(24),
		EARLY_LOGOUT(25),
		NO_LONGER_AT_RISK(26);

		 USER_ACCEPTANCE("KT01"),
		VEHICLE_CHECKS("KT02"),
		TEMPERATURE_CHECK("KT03"),
		CUSTOMER_REALISATION("KT04"),
		LOCATION_UPDATE("KT05"),
		CHANGE_PASSWORD("KT06"),
		BREAK("KT07"),
		INCIDENT_REPORT("KT08");

		{
			"driverId":"654321",
			"imei":"354201076822713",
			"latitude":"51.5406286",
			"longitude":"-0.1387482",
			"routeName":"XV00T1",
			"shiftStatus":0,
			"timestamp":"2016-08-26T13:45:11",
			"type":"KT05",
			"uuid":"0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6"
		}​

		 */

		Gson gson = new GsonBuilder().create();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'", Locale.UK);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

		List<OutboundMessage> queue = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId("36006");
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName("206298ORS");
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(8);
			outboundMessage.setType("KT05");
			outboundMessage.setData(new JsonObject());
			queue.add(outboundMessage);
		}

		for (int i = 0; i < 10; i++) {
			OutboundMessage outboundMessage = new OutboundMessage();
			outboundMessage.setLongitude("51.5406286");
			outboundMessage.setLatitude("-0.1387482");
			outboundMessage.setUuid("0dc6a5e9-7c8e-4c6d-aa9e-02a4f7fe30c6");
			outboundMessage.setImei("354201076822713");
			outboundMessage.setDriverId("36006");
			// NOTE we're actually sending the route ID back and not the route name
			outboundMessage.setRouteName("206298ORS");
			outboundMessage.setVehicleId("AX14BCU");
			outboundMessage.setTimestamp(dateFormatGmt.format(new Date()));
			outboundMessage.setShiftStatus(10);
			outboundMessage.setType("KT06");
			outboundMessage.setData(new JsonObject());
			queue.add(outboundMessage);
		}

		Collections.shuffle(queue);

		//int delayInMillis = 3600000 / 16500; // hour divided by number of messages
		//log.info("delay -> {}", delayInMillis);

		log.info("start: {}",  new Date().toString());
		for (OutboundMessage message : queue) {
			//log.info(message.getType());
			//Future<String> future = sendMessageService.sendMessage(gson.toJson(message));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("end: {}", new Date().toString());

	}

}