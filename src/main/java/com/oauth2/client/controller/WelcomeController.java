package com.oauth2.client.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/login")
	public String welcome(Model model) {
		

		ClientRegistration githubRegistration = clientRegistrationRepository.findByRegistrationId("github");
		ClientRegistration facebookAppRegistration = clientRegistrationRepository.findByRegistrationId("facebook");
		ClientRegistration myAppRegistration = clientRegistrationRepository.findByRegistrationId("myapp");

		model.addAttribute("githuburl", authorizationRequestBaseUri + "/" + githubRegistration.getRegistrationId());
		model.addAttribute("facebookUrl", authorizationRequestBaseUri + "/" + facebookAppRegistration.getRegistrationId());
		model.addAttribute("myappUrl", authorizationRequestBaseUri + "/" + myAppRegistration.getRegistrationId());
		return "welcome";
	}
	
	@GetMapping("/home")
	public String home( OAuth2AuthenticationToken authentication, Model model) {
		model.addAttribute("oauth_reg_id", authentication.getAuthorizedClientRegistrationId());
		return "home";
	}
	
	@RequestMapping("/unauthenticated")
	public String unauthenticated() {
	  return "redirect:/?error=true";
	}
	
	

	@GetMapping("/oauth/success")
	public String oauthSuccess() {
		return "redirect:/home";
	}
	
	@GetMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response, OAuth2AuthenticationToken authentication) {
	    if (authentication != null){ 
	        new SecurityContextLogoutHandler().logout(request, response, authentication);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}

}
