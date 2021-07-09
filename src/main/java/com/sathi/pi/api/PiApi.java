package com.sathi.pi.api;

import com.sathi.pi.model.BusArrivalResponse;
import com.sathi.pi.model.ExpiringHashMap;
import com.sathi.pi.model.MutliBusStopArrivalresponse;
import com.sathi.pi.model.RemoteControlRequest;
import com.sathi.pi.util.ApiCallEnum;
import com.sathi.pi.util.BashCommandExecutor;
import com.sathi.pi.util.WebServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PiApi {
	private static final Logger log = LoggerFactory.getLogger(PiApi.class);
	private static final ExpiringHashMap<String, BusArrivalResponse> busArrivalCache = new ExpiringHashMap<>(Duration.ofMinutes(1));

	@Autowired
	BashCommandExecutor commandExecutor;

	@Autowired
	WebServiceClient webServiceClient;

	@RequestMapping(method= RequestMethod.GET, value = "/getArrivalTiming", produces=MediaType.APPLICATION_JSON_VALUE)
	public MutliBusStopArrivalresponse getBusArrivalTimes(
			@RequestParam(value = "busStops") String busStops) {

		log.info("************************* BEGIN getBusArrivalTimes ************************* ");
		log.info("Gotten Reqeuest for Buststops:{}",busStops);
		MutliBusStopArrivalresponse mutliBusStopArrivalresponse = new MutliBusStopArrivalresponse();

		String[] busStopsArr = busStops.split(",");
		List<BusArrivalResponse> busArrivalRespList = getBusArrivals(busStopsArr);

		BusArrivalResponse[] busArrivalRespArr = new BusArrivalResponse[busArrivalRespList.size()];
		busArrivalRespArr = busArrivalRespList.toArray(busArrivalRespArr);
		mutliBusStopArrivalresponse.setBusArrivalResponse(busArrivalRespArr);
		log.debug(mutliBusStopArrivalresponse.toString());
		log.info("************************* END getBusArrivalTimes ************************* ");
		return mutliBusStopArrivalresponse;
	}

	private List<BusArrivalResponse> getBusArrivals(String[] busStopsArr) {
		List<BusArrivalResponse> busArrivalRespList = new ArrayList<>();
		for (String busStop : busStopsArr){
			if(busArrivalCache.get(busStop) == null){
				log.info("Calling REST service for [{}]", busStop);
				BusArrivalResponse response = webServiceClient.getRequest(ApiCallEnum.BUS_ARRIVAL, busStop, BusArrivalResponse.class);
				busArrivalCache.put(busStop, response);
			}
			busArrivalRespList.add(busArrivalCache.get(busStop));
		}
		return busArrivalRespList;
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
