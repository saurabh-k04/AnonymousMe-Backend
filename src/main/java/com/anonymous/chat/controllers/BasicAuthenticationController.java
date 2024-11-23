package com.anonymous.chat.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anonymous.chat.security.AuthenticationBean;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasicAuthenticationController {

	@GetMapping("/basicauth")
	public AuthenticationBean Auth() {
		return new AuthenticationBean("You are authenticated.");
	}
}
