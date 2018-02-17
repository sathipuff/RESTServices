package com.sathi.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusStopDetail {
/*
    BusStopCode The unique 5-digit identifier for this physical bus stop 01012
    RoadName The road on which this bus stop is locatedE.g.   Victoria St
    Description Landmarks next to the bus stop (if any) to aid in identifying this bus stop E.g.   Hotel Grand Pacific
    Latitude  Location coordinates for this bus stop 1.29685
    Longitude
    */

    @JsonProperty("BusStopCode")
    private String busStopCode;

    @JsonProperty("RoadName")
    private String roadName;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Latitude")
    private String latitude;

    @JsonProperty("Longitude")
    private String longitude;

    public String getBusStopCode() {
        return busStopCode;
    }

    public void setBusStopCode(String busStopCode) {
        this.busStopCode = busStopCode;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusStopDetail{");
        sb.append("busStopCode='").append(busStopCode).append('\'');
        sb.append(", roadName='").append(roadName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
