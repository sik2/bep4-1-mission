package com.back.boundedContext.post.app;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class PostFacade {
    private final PostSyncMemberUseCase postSyncMemberUseCase;
    private final PostWriteUseCase postWriteUseCase;
    private final PostSupport postSupport;

    public RsData<Post> write(PostMember author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional
    public PostMember syncMember(MemberDto memberDto) {
        return postSyncMemberUseCase.syncMember(memberDto);
    }

    public Long count() {
        return postSupport.count();
    }

    public Optional<Post> findById(int id) {
        return postSupport.findById(id);
    }

    public List<Post> findByOrderByIdDesc() {
        return postSupport.findByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findMemberByUsername(String username) {
        return postSupport.findMemberByUsername(username);
    }
}
