package com.back.boundedContext.post.app.query;

import com.back.boundedContext.post.out.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentQuery {

    private final CommentRepository commentRepository;

    public long count() {
        return commentRepository.count();
    }

}
