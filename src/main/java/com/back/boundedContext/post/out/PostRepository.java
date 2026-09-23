package com.back.boundedContext.post.out;

import java.util.List;

import com.back.boundedContext.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByOrderByIdDesc();
}