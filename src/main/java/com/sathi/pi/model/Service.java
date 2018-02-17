package com.sathi.pi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service {

	@JsonProperty("ServiceNo")
	private String serviceNo;
	
	@JsonProperty("NextBus")
	private NextBusDetail nextBusDetail1;
	
	@JsonProperty("NextBus2")
	private NextBusDetail nextBusDetail12;
	
	@JsonProperty("NextBus3")
	private NextBusDetail nextBusDetail13;

	@JsonProperty("DestinationCode")
	private String destinationCode;


	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	public NextBusDetail getNextBusDetail1() {
		return nextBusDetail1;
	}

	public void setNextBusDetail1(NextBusDetail nextBusDetail1) {
		this.nextBusDetail1 = nextBusDetail1;
	}

	public NextBusDetail getNextBusDetail12() {
		return nextBusDetail12;
	}

	public void setNextBusDetail12(NextBusDetail nextBusDetail12) {
		this.nextBusDetail12 = nextBusDetail12;
	}

	public NextBusDetail getNextBusDetail13() {
		return nextBusDetail13;
	}

	public void setNextBusDetail13(NextBusDetail nextBusDetail13) {
		this.nextBusDetail13 = nextBusDetail13;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Service [serviceNo=");
		builder.append(serviceNo);
		builder.append(", nextBusDetail1=");
		builder.append(nextBusDetail1);
		builder.append(", nextBusDetail12=");
		builder.append(nextBusDetail12);
		builder.append(", nextBusDetail13=");
		builder.append(nextBusDetail13);
		builder.append("destinationCode");
		builder.append(destinationCode);
		builder.append("]");
		return builder.toString();
	}
	
}
