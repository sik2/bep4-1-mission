package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureTipUseCase {
    private final MemberPolicy memberPolicy;

    public String getNeedToChangePasswordDays() {
        return "비밀번호 유효기간은 %d일입니다.".formatted(memberPolicy.getNeedToChangePasswordDays());
    }
}
