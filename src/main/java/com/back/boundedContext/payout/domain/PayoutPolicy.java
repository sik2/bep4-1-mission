package com.back.boundedContext.payout.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayoutPolicy {
    public static int PAYOUT_READY_WAITING_DAYS;

    @Value("${custom.payout.readyWaitingDays}")
    public void setPayoutReadyWaitingDays(int day) {
        PAYOUT_READY_WAITING_DAYS = day;
    }
}
