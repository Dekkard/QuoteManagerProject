package com.quotemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class QuoteManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuoteManagerApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QuoteManagerApplication.class);
	}
}