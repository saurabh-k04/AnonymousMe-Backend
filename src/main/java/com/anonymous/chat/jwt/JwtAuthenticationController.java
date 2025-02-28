package com.anonymous.chat.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anonymous.chat.entities.User;
import com.anonymous.chat.payload.SignupRequest;
import com.anonymous.chat.repositories.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JwtAuthenticationController {
	
	private final JwtTokenService tokenService;
    
    private final AuthenticationManager authenticationManager;
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationController(JwtTokenService tokenService, 
            AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/check")
    public String checking() {
    	return "checking...";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> generateToken(
            @RequestBody JwtTokenRequest jwtTokenRequest) {
        
        var authenticationToken = 
                new UsernamePasswordAuthenticationToken(
                        jwtTokenRequest.username(), 
                        jwtTokenRequest.password());
        
        var authentication = 
                authenticationManager.authenticate(authenticationToken);
        
        var token = tokenService.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest) {
    	if(userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
    		return ResponseEntity.badRequest().body("Username is already taken");
    	}
    	
    	User user = new User();
    	user.setUsername(signupRequest.getUsername());
    	user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    	
    	userRepository.save(user);
    	
    	return ResponseEntity.ok("User registered successfully");
    }
}
