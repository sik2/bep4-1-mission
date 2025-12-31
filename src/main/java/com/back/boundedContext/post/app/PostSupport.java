package com.back.boundedContext.post.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostSupport {
	private final PostRepository postRepository;
	private final PostMemberRepository postMemberRepository;

	public long count() {
		return postRepository.count();
	}

	public Optional<Post> findById(int id) {
		return postRepository.findById(id);
	}

	public Optional<PostMember> findMemberByUserName(String username) {
		return postMemberRepository.findByUserName(username);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public List<Post> findByOrderByIdDesc() {
		return postRepository.findByOrderByIdDesc();
	}
}
