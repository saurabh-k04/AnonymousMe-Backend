package com.hungergames.AnonymousMe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungergames.AnonymousMe.model.User;
import com.hungergames.AnonymousMe.repositories.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
	
    public boolean validateUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        System.out.println(optionalUser.get().getUsername()+optionalUser.get().getPassword());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Compare the password (without encoding/hashing for now)
            return user.getPassword().equals(password);
        }

        return false; // User not found
    }
}
