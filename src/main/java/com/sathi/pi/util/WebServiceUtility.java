package com.sathi.pi.util;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.sathi.pi.model.BusStopDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sathi.pi.api.PiApi;
import com.sathi.pi.model.BusArrivalResponse;
import com.sathi.pi.model.Service;

@Component
public class WebServiceUtility {
	private static final Logger log = LoggerFactory.getLogger(WebServiceUtility.class);
	private static final RestTemplate REST_TEMPLATE = new RestTemplate();
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	public static final MultiValueMap<String, String> LTA_WEB_HEADERS =new LinkedMultiValueMap<String, String>();

	@Value("${lta.webservices.apikey}")
	private String API_ACCOUNT_KEY;
	
	@PostConstruct
	public void init(){
		log.info("Using Account Key : {}",API_ACCOUNT_KEY);
		LTA_WEB_HEADERS.add("AccountKey", API_ACCOUNT_KEY);
	}
	
	public <T> T POSTService(ApiCallEnum apiCall, String methodParam, Class<T> respObjClass){
		String jsonResponse = "";
		try{
			HttpEntity<String> httpEntity = new HttpEntity<String>(null, LTA_WEB_HEADERS);
			log.debug("------------------------------REQUEST--------------------------");
			log.debug("Headers :{}",httpEntity.getHeaders().toString());
			log.debug(httpEntity.getBody());
			log.debug("------------------------------REQUEST--------------------------");
			ResponseEntity<String> res = REST_TEMPLATE.exchange(apiCall.getUrl() 	+ "?" +
																apiCall.getParam() 	+ "=" + methodParam, 
																HttpMethod.GET, httpEntity, String.class);
			jsonResponse = res.getBody();
			log.debug("------------------------------RESPONSE--------------------------");
			log.info(jsonResponse);
			log.debug("------------------------------RESPONSE--------------------------");
		}
		catch(Exception ex){
			log.error("Error Calling WebService",ex);
		}
		
		return parseJSONToObj(jsonResponse,respObjClass);
	}
	
	public <T> T parseJSONToObj(String responseBody, Class<T> respObjClass) {

		T responseObj = null;
		try {
			responseObj = respObjClass.newInstance();
		} catch (Exception e) {
			log.error("Error creating instance of T", e);
		}

		if (!StringUtils.isEmpty(responseBody)) {

			try {
				responseObj = JSON_MAPPER.readValue(responseBody, respObjClass);
			} catch (Exception e) {
				log.error("Error Parsing JSON", e);
			}
		}
		
		return responseObj;
	}

	public static void main(String[] args) {
		WebServiceUtility webServiceUtility = new WebServiceUtility();
		webServiceUtility.init();
		int skipCounter = 0;
		BusStopDetailsResponse response = null;
		while(response == null || response.getBusStopDetailArr().length > 1){
			response = webServiceUtility.POSTService(ApiCallEnum.BUS_STOP_INFO,String.valueOf(skipCounter), BusStopDetailsResponse.class);
			System.out.println(skipCounter);
			System.out.println(response.getBusStopDetailArr().length);
			skipCounter+=50;
		}
//		BusStopDetailsResponse response = webServiceUtility.POSTService(ApiCallEnum.BUS_STOP_INFO,"0", BusStopDetailsResponse.class);
		System.out.println(response.toString());
	}
	
}
