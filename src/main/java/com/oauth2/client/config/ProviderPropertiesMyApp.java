package com.oauth2.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.myapp")
public class ProviderPropertiesMyApp {
	String authorizationUri;
	String tokenUri;

}
