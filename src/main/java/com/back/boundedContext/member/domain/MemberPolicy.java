package com.back.boundedContext.member.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * MemberPolicy가 뭐냐
 * Member와 관련된 규칙을 한곳에 모은 것 (정책클래스)
 */
@Service
public class MemberPolicy {
    private static int PASSWORD_CHANGE_DAYS;

    @Value("${custom.member.password.changeDays}")
    public void setPasswordChangeDays(int days) {
        PASSWORD_CHANGE_DAYS = days;
    }
    public Duration getNeedToChangePasswordPeriod() {
        return Duration.ofDays(PASSWORD_CHANGE_DAYS);
    }

    public int getNeedToChangePasswordDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    public boolean isNeedToChangePassword(LocalDateTime lastChangedAt) {
        if (lastChangedAt == null) return true;

        return lastChangedAt.plusDays(PASSWORD_CHANGE_DAYS)
                .isBefore(LocalDateTime.now());
    }
}