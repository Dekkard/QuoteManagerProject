package com.quotemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quotemanager.model.Notification;
import com.quotemanager.service.StockService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
public class QuoteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteManagerApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QuoteManagerApplication.class);
	}
	
	@Autowired
	StockService ss;
	
	@Bean
	public void notifyServer() {
		ss.register(new Notification());
	}
}