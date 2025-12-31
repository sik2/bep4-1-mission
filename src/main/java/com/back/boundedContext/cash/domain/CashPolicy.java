package com.back.boundedContext.cash.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashPolicy {
        public static Long HOLDING_MEMBER_ID;

        @Value("${custom.global.holdingMemberId}")
        public void setHoldingMemberId(Long id) {
                HOLDING_MEMBER_ID = id;
        }
}

