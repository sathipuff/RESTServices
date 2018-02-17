package com.sathi.pi.api;

import java.net.UnknownHostException;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.sathi.pi.filter.CORSFilter;

@SpringBootApplication
@ComponentScan({"com.sathi.pi"})
public class PiSpringApplication {
	private static final Logger log = LoggerFactory.getLogger(PiSpringApplication.class);
	
	public static void main(final String[] args) throws UnknownHostException {
        SpringApplication.run(PiSpringApplication.class, args);
    }
	
	
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(initCORSFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("CORSFilter");
	    registration.setOrder(1);
	    return registration;
	} 
	

	public Filter initCORSFilter() {
	    return new CORSFilter();
	}
}
