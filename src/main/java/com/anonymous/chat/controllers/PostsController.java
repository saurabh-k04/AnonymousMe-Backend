package com.anonymous.chat.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anonymous.chat.entities.Post;
import com.anonymous.chat.entities.User;
import com.anonymous.chat.repositories.UserRepository;
import com.anonymous.chat.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "https://anonymous-ui.onrender.com")
@RestController
public class PostsController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@GetMapping("/users/{username}/posts")
//    public List<Post> getPostsForUser(@PathVariable String username, Authentication authentication) {
//        // Ensure the logged-in user matches the requested username
//        if (!authentication.getName().equals(username)) {
//            throw new SecurityException("You are not authorized to view posts for this user.");
//        }
//        return postService.findAll(username);
//    }
	
	@GetMapping("/posts")
	public List<Post> getAllPosts() {
	    return postService.findAll(); // Fetch all posts from the database
	}
	
//	@PostMapping("/users/{username}/posts")
//	public Post addPostForUser(@PathVariable String username, Authentication authentication, @RequestBody Post post) {
//	    // Ensure the logged-in user matches the username for the new post
//		System.out.println("In adding");
//	    if (!authentication.getName().equals(username)) {
//	        throw new SecurityException("You are not authorized to add posts for this user.");
//	    }
//	    
//	    // Retrieve the User entity by username
//	    User user = userRepository.findByUsername(username).get();
//	    if (user == null) {
//	        throw new IllegalArgumentException("User not found: " + username);
//	    }
//
//	    // Set the User to the Post entity
//	    post.setUser(user); 
//
//	    return postService.save(post); 
//	}
	
	@PostMapping(value = "/users/{username}/posts")
	public ResponseEntity<?> addPostForUser(
	        @PathVariable String username,
	        Authentication authentication,
	        @RequestPart("post") String postJson, 
	        @RequestPart(value = "image", required = false) MultipartFile file) {

	    try {
	        // Ensure logged-in user matches the username
	        if (!authentication.getName().equals(username)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body("You are not authorized to add posts for this user.");
	        }

	        // Convert JSON String to Post object
	        Post post = objectMapper.readValue(postJson, Post.class);

	        // Retrieve User entity by username
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

	        // Set user in Post entity
	        post.setUser(user);

	        // Save post using PostService
	        Post savedPost = postService.savePost(post, file);

	        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);

	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Invalid post data format: " + e.getMessage());
	    } catch (SecurityException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while creating the post.");
	    }
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
