package com.anonymous.chat.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anonymous.chat.entities.Post;
import com.anonymous.chat.repositories.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<Post> findAll() {
//		return postRepository.findByUserUsername(username);
		return postRepository.findAll();
	}
	
	public void deleteById(long id) {
		postRepository.deleteById(id);
	}
	
    public Post savePost(Post post, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            post.setImage(file.getBytes()); // Convert file to byte array and set in Post entity
        }
        return postRepository.save(post);
    }
	
//	public Post save(Post post) {
//		return postRepository.save(post);
//	}
	
	
//	public Post findById(long id) {
//		for(Post post: posts) {
//			if(post.getId() == id) {
//				return post;
//			}
//		}
//		return null;
//	}

}
