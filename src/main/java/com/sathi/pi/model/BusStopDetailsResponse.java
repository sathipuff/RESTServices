package com.sathi.pi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopDetailsResponse {

    @JsonProperty("value")
    private BusStopDetail[] busStopDetailArr;

    public BusStopDetail[] getBusStopDetailArr() {
        return busStopDetailArr;
    }

    public void setBusStopDetailArr(BusStopDetail[] busStopDetailArr) {
        this.busStopDetailArr = busStopDetailArr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusStopDetailsResponse{");
        sb.append("busStopDetailArr=").append(Arrays.toString(busStopDetailArr));
        sb.append('}');
        return sb.toString();
    }
}
