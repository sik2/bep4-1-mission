package com.back.boundedContext.cash.domain;


import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import com.back.shared.cash.dto.WalletDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder; //지갑의 소유자
    @Getter
    private long balance; // 잔고

    // 이 지갑에 대한 로그 리스트
    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    //잔고 체크
    public boolean hasBalance() {
        return balance > 0;
    }

    // -------------------- 입금 시 단계적으로 호출된다. --------------------------------------------
    // 오버로딩을 통해 단계적으로 값이 주입된다.
    // 이런 구조를 잡는 이유? 유연성 확보? 입력 편의성?
    // ------------------------------------------------------------------------------------------


    //입금 (입금액과 이벤트 타입만 받은 경우)
    public void credit(long amount, CashEventType eventType) {
        credit(amount, eventType, holder); //holder... 이 지갑의 소유자 정보를 실어서 보낸다
    }

    //입금 (입금액과 이벤트타입, relId를 받은 경우) relID = 이 지갑과 관계를 맺는 대상의 ID)
    public void credit(long amount, CashEventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId()); // rel의 클래스명과 Id를 실어서 보낸다
    }

    //입금 (입금액, 이벤트타입, relTypeCode, relId를 받은 경우) relTypeCode = rel의 클래스명이다
    public void credit(long amount, CashEventType eventType, String relTypeCode, Long relId) {
        balance += amount; // 최종 잔고 증감

        addCashLog(amount, eventType, relTypeCode, relId); //로그 기록
    }


    // -------------------- 출금 시 단계적으로 호출된다. --------------------------------------------
    // credit과 같은 구조
    // ------------------------------------------------------------------------------------------

    //출금
    public void debit(long amount, CashEventType eventType) {
        debit(amount, eventType, holder);
    }

    //출금
    public void debit(long amount, CashEventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    //출금
    public void debit(long amount, CashEventType eventType, String relTypeCode, Long relId) {
        balance -= amount;

        addCashLog(-amount, eventType, relTypeCode, relId);
    }


    //로그 작성
    private CashLog addCashLog(long amount, CashEventType eventType, String relTypeCode, Long relId) {
        CashLog cashLog = new CashLog(
                eventType,
                relTypeCode,
                relId,
                holder,
                this,
                amount,
                balance
        );

        cashLogs.add(cashLog);

        return cashLog;
    }

    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
    }
    public WalletDto toDto() {
        return new WalletDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                holder.getId(),
                holder.getUsername(),
                balance
        );
    }

}
