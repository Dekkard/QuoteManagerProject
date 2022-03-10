package com.quotemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@SpringBootApplication
public class QuoteManagerApplication {
	public static void main(String[] args) {
		String uri = "http://localhost:8080/notification/";
		Notification not = new Notification("localhost", 8081);
		RestTemplate rt = new RestTemplate();
		try {
			rt.postForObject(uri, not, HttpStatus.class);
		} catch (RestClientException e) {
		}

		SpringApplication.run(QuoteManagerApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QuoteManagerApplication.class);
	}
}

@Data
class Notification {
	private String host;
	private Integer port;

	public Notification(String host, Integer port) {
		this.host = host;
		this.port = port;
	}
}