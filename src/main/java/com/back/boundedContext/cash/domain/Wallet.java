package com.back.boundedContext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import com.back.shared.cash.dto.WalletDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private CashMember member;

    @Getter
    private long balance;

    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    public Wallet(CashMember member) {
        super(member.getId());
        this.member = member;
    }

    public WalletDto toDto() {
        return new WalletDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                member.getId(),
                member.getUsername(),
                balance
        );
    }

    public boolean hasBalance() {
        return balance > 0;
    }

    public void credit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance += amount;

        addCashLog(amount, eventType, relTypeCode, relId);
    }

    public void credit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void credit(long amount, CashLog.EventType eventType) {
        credit(amount, eventType, member);
    }

    public void debit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance -= amount;

        addCashLog(-amount, eventType, relTypeCode, relId);
    }

    public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(long amount, CashLog.EventType eventType) {
        debit(amount, eventType, member);
    }

    private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        CashLog cashLog = new CashLog(
                eventType,
                relTypeCode,
                relId,
                member,
                this,
                amount,
                balance
        );

        cashLogs.add(cashLog);

        return cashLog;
    }
}
