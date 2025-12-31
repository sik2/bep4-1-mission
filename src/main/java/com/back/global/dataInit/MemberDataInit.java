package com.back.global.dataInit;

import com.back.boundedContext.member.app.facade.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.app.facade.CommentFacade;
import com.back.boundedContext.post.app.facade.PostFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;


/**
 * 데이터 초기화를 위한 클래스
 */
@Configuration
@Slf4j

public class MemberDataInit {
    private final MemberDataInit self;
    private final MemberFacade memberFacade;
    private final PostFacade postFacade;
    private final CommentFacade commentFacade;

    public MemberDataInit(@Lazy MemberDataInit self, MemberFacade memberFacade, PostFacade postService, CommentFacade commentFacade) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postFacade = postService;
        this.commentFacade = commentFacade;

    }

    @Bean
    @Order(1)
    public ApplicationRunner MemberInitDataRunner() {
        return args -> {
            self.makeMemberMembers();
        };
    }

    @Transactional
    public void makeMemberMembers() {
        if (memberFacade.count() > 0) return;
        
        // Member 생성 - 이벤트를 통해 PostMember가 자동 생성됨
        Member systemMember = memberFacade.join("system", "1234", "시스템").getData();
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩").getData();
        Member adminMember = memberFacade.join("admin", "1234", "관리자").getData();
        Member user1Member = memberFacade.join("user1", "1234", "유저1").getData();
        Member user2Member = memberFacade.join("user2", "1234", "유저2").getData();
        Member user3Member = memberFacade.join("user3", "1234", "유저3").getData();
    }


}