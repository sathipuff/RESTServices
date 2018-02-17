package com.sathi.pi.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public enum ApiCallEnum {

	BUS_ARRIVAL(ApiCallEnum.LTA_WEB_BASE_URL + "/ltaodataservice/BusArrivalv2","BusStopCode"),
	BUS_STOP_INFO(ApiCallEnum.LTA_WEB_BASE_URL + "/ltaodataservice/BusStops","$skip");

	private String url;
	private String param;
	public static final String LTA_WEB_BASE_URL= "http://datamall2.mytransport.sg";
	
	ApiCallEnum(String url, String param){
		this.url = url;
		this.param = param;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
