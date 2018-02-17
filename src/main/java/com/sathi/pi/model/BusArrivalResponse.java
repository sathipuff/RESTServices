package com.sathi.pi.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusArrivalResponse {
	@JsonProperty("BusStopCode")
	public String busStopCode;
	
	@JsonProperty("Services")
	public Service[] services;
	
	@JsonProperty("RequestTs")
	public String requestTs = String.valueOf(System.currentTimeMillis());
	
	public String getBusStopCode() {
		return busStopCode;
	}

	public void setBusStopCode(String busStopCode) {
		this.busStopCode = busStopCode;
	}

	public Service[] getServices() {
		return services;
	}

	public void setServices(Service[] services) {
		this.services = services;
	}
	
	public String getRequestTs() {
		return requestTs;
	}

	public void setRequestTs(String requestTs) {
		this.requestTs = requestTs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusArrivalResponse [busStopCode=");
		builder.append(busStopCode);
		builder.append(",RequestTs=");
		builder.append(requestTs);
		builder.append(", services=");
		builder.append(Arrays.toString(services));
		builder.append("]");
		return builder.toString();
	}
	
	
}
