package com.back.boundedContext.cash.domain;


import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "CASH_CASH_LOG")
@NoArgsConstructor
public class CashLog extends BaseIdAndTime {

    @Enumerated(EnumType.STRING)
    private CashEventType cashEventType; // 이벤트 타입
    private String relTypeCode;         // 관계 타입 코드? 이게 정확히 뭐지
    private Long relId;                  // 관계 id? 이게 정확히 뭐지

    @ManyToOne(fetch = LAZY)
    private CashMember member;          // 이 로그의 소유자?
    @ManyToOne(fetch = LAZY)
    private Wallet wallet;              // 이 로그와 관계된 지갑

    private long amount;                // 증감액
    private long balance;               // 잔고

    public CashLog(CashEventType cashEventType, String relTypeCode, Long relId, CashMember member, Wallet wallet, long amount, long balance) {
        this.cashEventType = cashEventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.member = member;
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }
}