package com.back.boundedContext.post.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.post.domain.PostMember;

public interface PostMemberRepository extends JpaRepository<PostMember, Integer> {
	Optional<PostMember> findByUserName(String userName);
}
