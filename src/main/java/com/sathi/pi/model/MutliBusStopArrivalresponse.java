package com.sathi.pi.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MutliBusStopArrivalresponse {
	@JsonProperty("BusStops")
	BusArrivalResponse[] busArrivalResponse;

	public BusArrivalResponse[] getBusArrivalResponse() {
		return busArrivalResponse;
	}

	public void setBusArrivalResponse(BusArrivalResponse[] busArrivalResponse) {
		this.busArrivalResponse = busArrivalResponse;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MutliBusStopArrivalresponse [busArrivalResponse=");
		builder.append(Arrays.toString(busArrivalResponse));
		builder.append("]");
		return builder.toString();
	}
	
}
