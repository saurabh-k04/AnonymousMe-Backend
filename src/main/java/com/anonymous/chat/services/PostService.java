package com.anonymous.chat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anonymous.chat.entities.Post;

@Service
public class PostService {
	
	private static List<Post> posts = new ArrayList<>();
	private static int counter = 0;
	
	static {
		posts.add(new Post(++counter, "saurabh", "Learn Java"));
		posts.add(new Post(++counter, "saurabh", "Learn Angular"));
		posts.add(new Post(++counter, "saurabh", "Learn Microservices"));
		posts.add(new Post(++counter, "saurabh", "Learn React"));
	}
	
	public List<Post> findAll() {
		return posts;
	}
	
	public Post findById(long id) {
		for(Post post: posts) {
			if(post.getId() == id) {
				return post;
			}
		}
		return null;
	}
	
	public Post deleteById(long id) {
		Post post = findById(id);
		if(post == null) {
			return null;
		}
		
		if(posts.remove(post)) {
			return post;
		}
		return null;
	}
	
	public Post save(Post post) {
		System.out.println(post.getUsername());
		if(post.getId() == -1 || post.getId()==0) {
			post.setId(++counter);
			posts.add(post);
		}else {
			deleteById(post.getId());
			posts.add(post);
		}
		return post;
	}

}
