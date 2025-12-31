package com.back.boundedContext.post.app.facade;

import com.back.boundedContext.post.app.usecase.CreateCommentUseCase;
import com.back.boundedContext.post.domain.Comment;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.CommentRepository;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentRepository commentRepository;
    private final CreateCommentUseCase createCommentUseCase;

    public long count() {
        return commentRepository.count();
    }

    public RsData<Comment> createComment(Post post, PostMember author, String content){
        return createCommentUseCase.createComment(post, author, content);
    }

}
