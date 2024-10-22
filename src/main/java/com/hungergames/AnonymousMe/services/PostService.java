package com.hungergames.AnonymousMe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungergames.AnonymousMe.model.Post;
import com.hungergames.AnonymousMe.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
	
}
