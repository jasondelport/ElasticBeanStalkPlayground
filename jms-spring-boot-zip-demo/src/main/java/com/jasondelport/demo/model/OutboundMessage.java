package com.jasondelport.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.jasondelport.demo.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundMessage {

	private static final Logger log = LoggerFactory.getLogger(OutboundMessage.class);

	private String timestamp;
	private String longitude;
	private String latitude;
	private String uuid;
	private String imei;
	private String driverId;
	// NOTE we're actually sending the route ID back and not the route name
	private String routeName;
	private String vehicleId;
	@JsonIgnore
	private JsonObject data;
	@JsonProperty("data")
	private transient JsonNode jsonNode;
	private String type;
	private int shiftStatus;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
		this.jsonNode = Utils.gsonToJackson(data);
	}

	public JsonNode getJsonNode() {
		return jsonNode;
	}

	public void setJsonNode(JsonNode jsonNode) {
		this.jsonNode = jsonNode;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getShiftStatus() {
		return shiftStatus;
	}

	public void setShiftStatus(int shiftStatus) {
		this.shiftStatus = shiftStatus;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public String toString() {
		return Utils.toJsonString(this);
	}
}
