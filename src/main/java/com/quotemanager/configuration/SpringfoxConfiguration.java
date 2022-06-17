package com.quotemanager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quotemanager.model.Notification;
import com.quotemanager.model.DTO.Message;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxConfiguration {
	@Value("${project.version}")
	private String version;
	
	@Bean
	public Docket QuoteManagerAPI() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select()//
				.apis(RequestHandlerSelectors.basePackage("com.quotemanager"))//
				.paths(PathSelectors.ant("/**"))//
				.build()
				.ignoredParameterTypes(Message.class, Notification.class)
				.apiInfo(new ApiInfoBuilder()//
						.title("Quote Manager")//
						.description("Application that manages the quote stocks of the Stock Manager application.\nThis API provides endpoints that enables the search, insert and update of quotes that exists in the Stock Manager.\nIt's also capable of sending requests to create or update stocks.")
						.version(version)
						.contact(new Contact("Gustavo Rocha Flores", "https://github.com/dekkard", "gustavo.rocha@inatel.br"))
						.build());
	}
}
 