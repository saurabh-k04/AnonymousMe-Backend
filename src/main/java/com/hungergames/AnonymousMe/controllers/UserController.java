package com.hungergames.AnonymousMe.controllers;

import java.util.HashMap;
import java.util.Map;

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
    
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
    	if (userService.findByUsername(user.getUsername()).isPresent()) {
    	    Map<String, String> response = new HashMap<>();
    	    response.put("message", "Username is already taken!");
    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	}

    	userService.saveUser(user);
    	Map<String, String> response = new HashMap<>();
    	response.put("message", "User registered successfully!");
    	return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
    	System.out.println(user.getUsername()+user.getPassword());
        Map<String, String> response = new HashMap<>();

        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            response.put("message", "Login successfull!");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Invalid credentials.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

}
