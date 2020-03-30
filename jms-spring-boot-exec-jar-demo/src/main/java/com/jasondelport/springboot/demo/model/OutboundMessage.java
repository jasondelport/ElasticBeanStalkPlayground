package com.jasondelport.springboot.demo.model;

import com.google.gson.JsonObject;

public class OutboundMessage {

	private String timestamp;
	private String longitude;
	private String latitude;
	private String uuid;
	private String imei;
	private String driverId;
	// NOTE we're actually sending the route ID back and not the route name
	private String routeName;
	private String vehicleId;
	private JsonObject data;
	private String type;
	private int shiftStatus;
	private long epochTimestamp;

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

	public long getEpochTimestamp() {
		return epochTimestamp;
	}

	public void setEpochTimestamp(long epochTimestamp) {
		this.epochTimestamp = epochTimestamp;
	}

	@Override
	public String toString() {
		return "OutboundMessage{" +
				"timestamp='" + timestamp + '\'' +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", uuid='" + uuid + '\'' +
				", imei='" + imei + '\'' +
				", driverId='" + driverId + '\'' +
				", routeName='" + routeName + '\'' +
				", vehicleId='" + vehicleId + '\'' +
				", data=" + data +
				", type='" + type + '\'' +
				", shiftStatus=" + shiftStatus +
				'}';
	}
}
