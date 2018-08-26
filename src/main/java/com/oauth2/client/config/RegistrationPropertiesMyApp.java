package com.oauth2.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Configuration 
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.myapp")
public class RegistrationPropertiesMyApp {
	String clientId;
	String clientSecret;
	String authorizationGrantType;
	String clientAuthenticationMethod;
	String redirectUriTemplate;
	String scope;
	
}
