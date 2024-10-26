package com.hungergames.AnonymousMe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for development; enable in production
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/users/signup", "/api/users/login").permitAll() // Allow access to signup and login
//            .requestMatchers("/api/users/signup").authenticated() // Require authentication for signup
            .anyRequest().authenticated() // Secure all other endpoints
        )
        .httpBasic(httpSecurityHttpBasicConfigurer -> {}); // Enable HTTP Basic Authentication without additional config

        return http.build();
    }
}
