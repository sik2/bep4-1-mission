package com.back.boundedContext.post.app;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostQueryUseCase {
    private final PostRepository postRepository;

    public Long count() {
        return postRepository.count();
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
}
