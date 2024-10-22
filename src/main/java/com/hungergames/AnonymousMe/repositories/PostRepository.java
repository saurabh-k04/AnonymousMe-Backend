package com.hungergames.AnonymousMe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungergames.AnonymousMe.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
