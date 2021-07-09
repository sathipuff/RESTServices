package com.sathi.pi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Component
public class WebServiceClient {
    private static final Logger log = LoggerFactory.getLogger(WebServiceClient.class);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Value("${lta.webservices.apikey}")
    private String API_ACCOUNT_KEY;

    @PostConstruct
    public void init() {
        log.info("Using Account Key : {}", API_ACCOUNT_KEY);
    }

    public <T> T getRequest(ApiCallEnum apiCall, String methodParam, Class<T> respObjClass) {
        HttpURLConnection conn = null;
        StringBuilder outputBuilder = new StringBuilder();
        try {

            String urlQuery = apiCall.getUrl() + "?" + apiCall.getParam() + "=" + methodParam;
            URL url = new URL(urlQuery);
            log.info("Calling : [{}]", urlQuery);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("AccountKey", API_ACCOUNT_KEY);

            if (conn.getResponseCode() != 200) {
                log.error("Error connection with error code : {}", conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())))) {
                String output;
                while ((output = br.readLine()) != null) {
                    outputBuilder.append(output);
                }
                log.info("Gotten response : [{}]", outputBuilder.toString());
            }
        } catch (Exception ex) {
            log.error("Error while connecting to REST Service: ", ex);

        } finally {
            if (Objects.nonNull(conn)) {
                conn.disconnect();
            }

        }
        return parseJSONToObj(outputBuilder.toString(), respObjClass);
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
}
