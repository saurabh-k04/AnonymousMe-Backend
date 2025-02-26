package com.anonymous.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anonymous.chat.entities.Post;
import com.anonymous.chat.entities.User;
import com.anonymous.chat.repositories.UserRepository;
import com.anonymous.chat.services.PostService;

@CrossOrigin(origins = "https://anonymousmechat-backend.onrender.com")
@RestController
public class PostsController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users/{username}/posts")
    public List<Post> getPostsForUser(@PathVariable String username, Authentication authentication) {
        // Ensure the logged-in user matches the requested username
        if (!authentication.getName().equals(username)) {
            throw new SecurityException("You are not authorized to view posts for this user.");
        }
        return postService.findAll(username);
    }
	
	@PostMapping("/users/{username}/posts")
	public Post addPostForUser(@PathVariable String username, Authentication authentication, @RequestBody Post post) {
	    // Ensure the logged-in user matches the username for the new post
		System.out.println("In adding");
	    if (!authentication.getName().equals(username)) {
	        throw new SecurityException("You are not authorized to add posts for this user.");
	    }
	    
	    // Retrieve the User entity by username
	    User user = userRepository.findByUsername(username).get();
	    if (user == null) {
	        throw new IllegalArgumentException("User not found: " + username);
	    }

	    // Set the User to the Post entity
	    post.setUser(user); 

	    return postService.save(post); 
	}

//    @PostMapping("/users/{username}/posts")
//    public Post addPostForUser(@PathVariable String username, Authentication authentication, @RequestBody Post post) {
//        // Ensure the logged-in user matches the username for the new post
//        if (!authentication.getName().equals(username)) {
//            throw new SecurityException("You are not authorized to add posts for this user.");
//        }
//        post.setUsername(username); // Associate the post with the logged-in user
//        return postService.save(post);
//    }
	
//	@GetMapping("/users/{username}/posts")
//	public List<Post> getAllPostsForUser(Authentication authentication){
//		String username = authentication.getName();
//		return postService.findAll(username);	
//	}
//	
//	@PostMapping("/users/{username}/posts")
//	public Post createPost(@RequestBody Post post, Authentication authentication) {
//		String username = authentication.getName();
//		post.getUser().setUsername(username);
//		return postService.save(post);
//	}
	
//	@GetMapping("/users/{username}/posts/{id}")
//	public Post getPost(@PathVariable String username, @PathVariable long id){
//		return postService.findById(id);	
//	}
	
//	@PutMapping("/users/{username}/posts/{id}")
//	public ResponseEntity<Post> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Post post) {
//		Post postUpdated = postService.save(post);
//		return new ResponseEntity<Post>(post, HttpStatus.OK);
//	}

}
