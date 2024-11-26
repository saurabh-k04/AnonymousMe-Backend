package com.anonymous.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anonymous.chat.entities.Post;
import com.anonymous.chat.repositories.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<Post> findAll(String username) {
		return postRepository.findByUserUsername(username);
	}
	
	public void deleteById(long id) {
		postRepository.deleteById(id);
	}
	
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	
//	public Post findById(long id) {
//		for(Post post: posts) {
//			if(post.getId() == id) {
//				return post;
//			}
//		}
//		return null;
//	}

}
