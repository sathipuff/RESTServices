package com.sathi.pi.api;

import java.util.*;

import com.sathi.pi.model.RemoteControlRequest;
import com.sathi.pi.util.BashCommandExecutor;
import com.sathi.pi.util.WebServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.sathi.pi.model.BusArrivalResponse;
import com.sathi.pi.model.MutliBusStopArrivalresponse;
import com.sathi.pi.util.ApiCallEnum;

@RestController
public class PiApi {
	private static final Logger log = LoggerFactory.getLogger(PiApi.class);

	@Autowired
	BashCommandExecutor commandExecutor;

	@Autowired
	WebServiceClient webServiceClient;

	@RequestMapping(method= RequestMethod.GET, value = "/getArrivalTiming", produces=MediaType.APPLICATION_JSON_VALUE)
	public MutliBusStopArrivalresponse getBusArrivalTimes(
			@RequestParam(value = "busStops") String busStops) {
		
		log.info("************************* BEGIN getBusArrivalTimes ************************* ");
		log.info("Gotten Reqeuest for Buststops:{}",busStops);
		List<BusArrivalResponse> busArrivalRespList = new ArrayList<BusArrivalResponse>();
		MutliBusStopArrivalresponse mutliBusStopArrivalresponse = new MutliBusStopArrivalresponse();

		String[] busStopsArr = busStops.split(",");
		for (String busStop : busStopsArr){
			if(!StringUtils.isEmpty(busStop)){
				BusArrivalResponse response = webServiceClient.getRequest(ApiCallEnum.BUS_ARRIVAL, busStop, BusArrivalResponse.class);
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


	@PostMapping(value = "/remoteControlAction", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String executeRemoteControlCommand(
			@RequestBody RemoteControlRequest request) {
		log.info("************************* BEGIN Remote Control Request ************************* ");
		log.info("Gotten Request for remote control :{} to : {}",request.getComponent(), request.getCommand());
		commandExecutor.executePython3Command(request.getComponent(), request.getCommand());
		log.info("************************* END Remote Control Request ************************* ");
		return "OK";
	}
}
