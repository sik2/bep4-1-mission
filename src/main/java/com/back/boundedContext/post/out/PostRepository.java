package com.back.boundedContext.post.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByOrderByIdDesc();
}
