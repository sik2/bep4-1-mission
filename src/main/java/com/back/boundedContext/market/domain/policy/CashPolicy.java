package com.back.boundedContext.market.domain.policy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashPolicy {
    public static int HOLDING_MEMBER_ID;

    @Value("${custom.global.holdingMemberId}")
    public void setHoldingMemberId(int id) {
        HOLDING_MEMBER_ID = id;
    }
}
