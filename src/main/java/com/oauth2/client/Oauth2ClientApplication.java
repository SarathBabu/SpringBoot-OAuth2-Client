package com.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class Oauth2ClientApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(Oauth2ClientApplication.class, args);
	}
	

}
