package com.anonymous.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anonymous.chat.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserUsername(String username);
}
