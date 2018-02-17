package com.sathi.pi.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sathi.pi.model.BusArrivalResponse;
import com.sathi.pi.model.MutliBusStopArrivalresponse;
import com.sathi.pi.util.ApiCallEnum;
import com.sathi.pi.util.WebServiceUtility;

@RestController
public class PiApi {
	private static final Logger log = LoggerFactory.getLogger(PiApi.class);
	@Autowired
	WebServiceUtility webServiceUtility;

	@RequestMapping(method= RequestMethod.GET, value = "/getArrivalTiming", produces=MediaType.APPLICATION_JSON_VALUE)
	public MutliBusStopArrivalresponse getBusArrivalTimes(
			@RequestParam(value = "busStops") String busStops) {
		log.info("************************* BEGIN getBusArrivalTimes ************************* ");
		log.info("Gotten Reqeuest for Buststops:{}",busStops);
		List<BusArrivalResponse> busArrivalRespList = new ArrayList<BusArrivalResponse>();
		MutliBusStopArrivalresponse mutliBusStopArrivalresponse = new MutliBusStopArrivalresponse();
		
		for (String busStop : busStops.split(",")){
			if(!StringUtils.isEmpty(busStop)){
				BusArrivalResponse response = webServiceUtility.POSTService(ApiCallEnum.BUS_ARRIVAL, busStop, BusArrivalResponse.class);
				busArrivalRespList.add(response);
			}
		}
		
		BusArrivalResponse[] busArrivalRespArr = new BusArrivalResponse[busArrivalRespList.size()];
		busArrivalRespArr = busArrivalRespList.toArray(busArrivalRespArr);
		mutliBusStopArrivalresponse.setBusArrivalResponse(busArrivalRespArr);
		log.debug(mutliBusStopArrivalresponse.toString());
		log.info("************************* END getBusArrivalTimes ************************* ");
		return mutliBusStopArrivalresponse;
	}
}
