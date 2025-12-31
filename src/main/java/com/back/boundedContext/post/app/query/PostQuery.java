package com.back.boundedContext.post.app.query;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.PostMemberRepository;
import com.back.boundedContext.post.out.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostQuery {

    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;

    public Optional<Post> findByPostId(int i) {
        return postRepository.findById(i);
    }

    public long count() {
        return postRepository.count();
    }

    public Optional<PostMember> findPostMemberById(Long id) {
        return postMemberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findPostMemberByUsername(String username) {
        return postMemberRepository.findByUsername(username);
    }

    public List<Post> findByOrderByIdDesc() {
        return postRepository.findByOrderByIdDesc();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

}
