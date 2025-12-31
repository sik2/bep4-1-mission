package com.back.boundedContext.member.app.usecase;

import com.back.boundedContext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomTipUseCase {

    private final MemberPolicy memberPolicy;

    public String getRandomSecureTip() {
        return "비밀번호의 유효기간은 %d일 입니다."
                .formatted(memberPolicy.getNeedToChangePasswordDays());
    }
}
