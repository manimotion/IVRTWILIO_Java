package com.example.twilio.calls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwilioJavaHandlePhoneCallsApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8082");

		SpringApplication.run(TwilioJavaHandlePhoneCallsApplication.class, args);
	}
}
