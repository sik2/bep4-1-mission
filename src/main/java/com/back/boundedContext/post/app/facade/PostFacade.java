package com.back.boundedContext.post.app.facade;

import com.back.boundedContext.post.app.query.PostQuery;
import com.back.boundedContext.post.app.usecase.CreatePostUseCase;
import com.back.boundedContext.post.app.usecase.PostSyncUseCase;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.PostMemberRepository;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Facade는 이 BoundedContext 안으로 들어오는 모든 요청에 대한 관문이다.
 * Post 컨텍스트는 PostMember만 사용하며 Member 컨텍스트와의 직접 결합을 피한다.
 */
@Service
@RequiredArgsConstructor
public class PostFacade {

    private final CreatePostUseCase createPostUseCase;
    private final PostMemberRepository postMemberRepository;
    private final PostSyncUseCase postSyncUseCase;

    private final PostQuery postQuery;


    public RsData<Post> createPost(String title, PostMember author, String content){
        return createPostUseCase.CreatePost(title, author, content);
    }

    public Optional<Post> findByPostId(int i) {
        return postQuery.findByPostId(i);
    }
    
    public long count() {
        return postQuery.count();
    }
    
    public Optional<PostMember> findPostMemberById(Long id) {
        return postQuery.findPostMemberById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<PostMember> findPostMemberByUsername(String username) {
        return postQuery.findPostMemberByUsername(username);
    }

    @Transactional
    public PostMember syncMember(MemberDto member) {
        return postSyncUseCase.syncMember(member);
    }

    @Transactional(readOnly = true)
    public List<Post> findByOrderByIdDesc() {
        return postQuery.findByOrderByIdDesc();
    }

}
