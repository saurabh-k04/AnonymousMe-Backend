package com.anonymous.chat.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.anonymous.chat.entities.User;
import com.anonymous.chat.payload.OtpRequest;
import com.anonymous.chat.payload.SignupRequest;
import com.anonymous.chat.repositories.UserRepository;
import com.anonymous.chat.services.OTPService;

@CrossOrigin(origins = "https://anonymous-ui.onrender.com")
@RestController
@RequestMapping("/auth")
public class OtpAuthController {
    @Autowired
    private OTPService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, String> userSignupData = new HashMap<>(); // Temporarily store user data

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody SignupRequest signupRequest) {
        if (!isValidEmail(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        String otp = otpService.generateOTP(signupRequest.getUsername());
        userSignupData.put(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()));

        return ResponseEntity.ok("OTP sent to email");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
        if (!otpService.validateOTP(request.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        if (!userSignupData.containsKey(request.getEmail())) {
            return ResponseEntity.badRequest().body("No signup data found for this email");
        }

        // Create and save user
        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(userSignupData.get(request.getEmail()));

        userRepository.save(user);

        // Clean up temporary data
        userSignupData.remove(request.getEmail());

        return ResponseEntity.ok("User registered successfully");
    }

}

