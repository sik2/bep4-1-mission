package com.back.boundedContext.post.app;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostMemberQueryUseCase {
    private final PostMemberRepository postMemberRepository;

    public Optional<PostMember> findByUsername(String username) {
        return postMemberRepository.findByUsername(username);
    }
}
