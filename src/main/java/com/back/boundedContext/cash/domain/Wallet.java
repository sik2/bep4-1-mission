package com.back.boundedContext.cash.domain;

import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualAndTime;
import com.back.shared.cash.dto.WalletDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualAndTime {
	@ManyToOne(fetch = FetchType.LAZY)
	private CashMember holder;

	@Getter
	private long balance;

	@OneToMany(mappedBy = "wallet", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<CashLog> cashLogs = new ArrayList<>();

	public Wallet(CashMember holder) {
		super(holder.getId());
		this.holder = holder;
	}

	public WalletDto toDto() {
		return new WalletDto(
			getId(),
			getCreateTime(),
			getModifyTime(),
			holder.getId(),
			holder.getUserName(),
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
		credit(amount, eventType, holder);
	}

	public void debit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
		balance -= amount;

		addCashLog(amount, eventType, relTypeCode, relId);
	}

	public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
		debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
	}

	public void debit(long amount, CashLog.EventType eventType) {
		debit(amount, eventType, holder);
	}

	private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
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
}
