package com.oauth2.client.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@EnableWebSecurity
@Order(2)
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
	@Autowired
	private Environment env;

	@Autowired
	RegistrationPropertiesMyApp myAppRegistration;

	@Autowired
	ProviderPropertiesMyApp myAppProviider;

	private List<String> clients = Arrays.asList("github","facebook", "myapp");

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/webjars/**", "/static/**", "/css/**", "/loginFailure").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.authorizeRequests().antMatchers("/**").authenticated().and().logout().logoutUrl("/logout").and()
				.oauth2Login()
				.loginPage("/login").authorizationEndpoint().baseUri("/oauth2/authorize-client")
				.authorizationRequestRepository(authorizationRequestRepository())
				.and().tokenEndpoint()
				.accessTokenResponseClient(accessTokenResponseClient())
				.and().defaultSuccessUrl("/oauth/success")
				.failureUrl("/unauthenticated").authorizedClientService(authorizedClientService());
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		return new NimbusAuthorizationCodeTokenResponseClient();
	}

	@Bean
	InMemoryClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream().map(c -> getRegistration(c))
				.filter(registration -> registration != null).collect(Collectors.toList());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	private ClientRegistration getRegistration(String client) {
		String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");

		if (clientId == null) {
			return null;
		}

		String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");
		String redirectUri = env.getProperty(CLIENT_PROPERTY_KEY + client + ".redirect-uri-template");

		if (client.equals("github")) {
			return CommonOAuth2Provider.GITHUB.getBuilder(client).clientId(clientId).clientSecret(clientSecret)
					.redirectUriTemplate(redirectUri).build();
		} else if (client.equals("facebook")) {
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client).clientId(clientId).clientSecret(clientSecret)
					.redirectUriTemplate(redirectUri).build();
		}else if (client.equals("myapp")) {
			return ClientRegistration.withRegistrationId(client).clientId("usdk2gpd08ove78a126e")
					.clientSecret("9qya1d8e958bi77m03trq2zimn2lwdivtmv16t55")
					.redirectUriTemplate("http://localhost:8082/login/oauth2/code/myapp")
					.authorizationGrantType(AuthorizationGrantType.IMPLICIT)
					.authorizationUri("http://localhost:8089/oauth/authorize")
					.tokenUri("http://localhost:8089/oauth/token").scope("read", "write").clientName("MyApp")
					.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC).build();
		}
		return null;
	}

}
