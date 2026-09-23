package com.back.boundedContext.post.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostFacade {
    private final PostWriteUseCase postWriteUseCase;
    private final PostSupporter postSupporter;
    private final PostMemberSyncUseCase postMemberSyncUseCase;

    public RsData<Post> write(PostMember author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    public void syncMember(MemberDto memberDto) {
        postMemberSyncUseCase.syncMember(memberDto);
    }

    public long count() {
        return postSupporter.count();
    }

    public Optional<Post> findById(int id) {
        return postSupporter.findById(id);
    }

    public Optional<PostMember> findPostMemberByUsername(String username) {
        return postSupporter.findPostMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Post> findByOrderByIdDesc() {
        return postSupporter.findByOrderByIdDesc();
    }
}
