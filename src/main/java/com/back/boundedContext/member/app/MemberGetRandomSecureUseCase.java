package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureUseCase {
    private final MemberPolicy memberPolicy;

    public String getRandomSecureTip() {
        int pwdExpiredDays = memberPolicy.getNeedToChangePasswordDays();
        return "비밀번호의 유효기간은 %d 일 입니다.".formatted(pwdExpiredDays);
    }
}
