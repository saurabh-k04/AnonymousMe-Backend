package com.hungergames.AnonymousMe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungergames.AnonymousMe.model.User;
import com.hungergames.AnonymousMe.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Username is already taken!\"}");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("{\"message\": \"User registered successfully!\"}");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("{\"message\": \"Login successful!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials!\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    
//    Old Version
//    @PostMapping("/signup")
//    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
//    	Map<String, String> response = new HashMap<>();
//    	if (userService.findByUsername(user.getUsername()).isPresent()) {
//    	    response.put("message", "Username is already taken!");
//    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
////    	    return ResponseEntity.badRequest().body("{\"message\": \"Username is already taken!\"}");
//    	}
//    	userService.saveUser(user);
//    	response.put("message", "User registered successfully!");
//    	return ResponseEntity.ok(response);
////        return ResponseEntity.ok("{\"message\": \"User registered successfully!\"}");
//    }
    
//    Old Version
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User loginUser) {
//    	System.out.println(loginUser.getUsername()+loginUser.getPassword());
//        Map<String, String> response = new HashMap<>();
//
////      Fetch user by username
//        Optional<User> userOpt = userService.findByUsername(loginUser.getUsername());
//        
//        if (userOpt.isEmpty()) {
//        	response.put("message", "User does not exist!");
//        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//        
//        User user = userOpt.get();
//        
//        if (!userService.validatePassword(loginUser.getPassword(), user.getPassword())) {
//        	response.put("message", "User does not exist!");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//        
////        if (userService.validateUser(user)) {
////            response.put("message", "Login successfull!");
////            return ResponseEntity.ok(response);
////        }
//
//        response.put("message", "Login successfull!");
//        return ResponseEntity.ok(response);
//    }
}
