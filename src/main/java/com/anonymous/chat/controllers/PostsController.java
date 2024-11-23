package com.anonymous.chat.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anonymous.chat.entities.Post;
import com.anonymous.chat.services.PostService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class PostsController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/users/{username}/posts")
	public List<Post> getAllPosts(@PathVariable String username){
		return postService.findAll();	
	}
	
	@GetMapping("/users/{username}/posts/{id}")
	public Post getPost(@PathVariable String username, @PathVariable long id){
		return postService.findById(id);	
	}
	
	@PutMapping("/users/{username}/posts/{id}")
	public ResponseEntity<Post> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Post post) {
		Post postUpdated = postService.save(post);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
	
	@PostMapping("/users/{username}/posts")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Post post) {
		Post createdPost = postService.save(post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdPost.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

}
