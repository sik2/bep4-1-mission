package com.back.boundedContext.member.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MemberPolicy {
    private static int PASSWORD_CHANGE_DAYS;

    @Value("${custom.member.password.changeDays}")
    public void setPasswordChangeDays(int days) {
        PASSWORD_CHANGE_DAYS = days;
    }

    // 비밀번호 변경이 필요한 기간을 Duration 객체로 반환
    public Duration getNeedToChangePasswordPeriod() {
        return Duration.ofDays(PASSWORD_CHANGE_DAYS);
    }

    // 비밀번호 변경이 필요한 일수를 정수로 반환
    public int getNeedToChangePasswordDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    // 비밀번호 변경이 필요한지 판단하는 메서드
    public boolean isNeedToChangePassword(LocalDateTime lastChangedAt) {
        // 비밀번호를 한 번도 변경하지 않은 경우
        if (lastChangedAt == null) return true;

        // 마지막 변경일 + 90일이 현재 시간보다 이전인지 확인
        // 예: 마지막 변경일이 2024.01.01 -> 2024.04.01 (90일 후)
        //     현재가 2024.05.01이면 -> isBefore = true -> 변경 필요
        return lastChangedAt.plusDays(PASSWORD_CHANGE_DAYS)
                .isBefore(LocalDateTime.now());
    }
}
