package com.sathi.pi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NextBusDetail {
	@JsonProperty("EstimatedArrival")
	private String estimatedArrival;
	
	
/*	Current bus occupancy / crowding level:
		 SEA (for Seats Available)
		 SDA (for Standing Available)
		 LSD (for Limited Standing)*/

	@JsonProperty("Load")
	private String load;


	public String getEstimatedArrival() {
		return estimatedArrival;
	}


	public void setEstimatedArrival(String estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}


	public String getLoad() {
		return load;
	}


	public void setLoad(String load) {
		this.load = load;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NextBusDetail [estimatedArrival=");
		builder.append(estimatedArrival);
		builder.append(", load=");
		builder.append(load);
		builder.append("]");
		return builder.toString();
	} 
	
	
}
