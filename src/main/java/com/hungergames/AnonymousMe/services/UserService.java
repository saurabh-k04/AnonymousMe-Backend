package com.hungergames.AnonymousMe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hungergames.AnonymousMe.model.User;
import com.hungergames.AnonymousMe.repositories.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // Encrypt the password during signup
    public void saveUser(User user) {
        // Encrypt the password using PasswordEncoder (BCrypt)
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
    
    // Verify the password during login
    public boolean authenticate(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Compare raw password with encrypted password
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    
//    Old Version
//    public User saveUser(User user) {
//    	String encodedPassword = passwordEncoder.encode(user.getPassword());
//    	user.setPassword(encodedPassword);
////    	Save user to database
//        return userRepository.save(user);
//    }
    
//    Old Version
//    public boolean checkPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//    
//    // Validate the password during login
//    public boolean validatePassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
	
//    public boolean validateUser(User user) {
//        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
//        System.out.println(optionalUser.get().getUsername()+optionalUser.get().getPassword());
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            // Compare the password (without encoding/hashing for now)
//            return user.getPassword().equals(password);
//        }
//
//        return false; // User not found
//    }
}
