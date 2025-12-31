package com.back.boundedContext.member.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByUserName(String userName);
}
