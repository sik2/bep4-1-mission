package com.back.boundedContext.member.in;

import com.back.global.response.RsData;
import com.back.boundedContext.cash.app.CashFacade;
import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.app.MemberJoinUseCase;
import com.back.boundedContext.member.domain.Member;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class MemberDataInit {
    private final MemberDataInit self;
    private final MemberFacade memberFacade;
    private final CashFacade cashFacade;
    private final MemberJoinUseCase memberJoinUseCase;

    public MemberDataInit(
            @Lazy MemberDataInit self,
            MemberFacade memberFacade,
            CashFacade cashFacade,
            MemberJoinUseCase memberJoinUseCase
    ) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.cashFacade = cashFacade;
        this.memberJoinUseCase = memberJoinUseCase;
    }

    @Order(1)
    @Bean
    public ApplicationRunner MemberDataInitRunner() {
        return args -> {
            self.makeBaseMembers();
        };
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberFacade.count() > 0) return;

        RsData<Member> systemMember = memberJoinUseCase.join("system", "1234", "시스템");
        RsData<Member> holdingMember = memberJoinUseCase.join("holding", "1234", "홀딩");
        RsData<Member> adminMember = memberJoinUseCase.join("admin", "1234", "관리자");
        RsData<Member> user1Member = memberJoinUseCase.join("user1", "1234", "유저1");
        RsData<Member> user2Member = memberJoinUseCase.join("user2", "1234", "유저2");
        RsData<Member> user3Member = memberJoinUseCase.join("user3", "1234", "유저3");
    }
}
