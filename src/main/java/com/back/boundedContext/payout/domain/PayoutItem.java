package com.back.boundedContext.payout.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import com.back.global.jpa.entity.BaseIdAndTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYOUT_PAYOUT_ITEM")
@NoArgsConstructor
public class PayoutItem extends BaseIdAndTime {
	@ManyToOne(fetch = LAZY)
	private Payout payout;
	@Enumerated(EnumType.STRING)
	private PayoutEventType eventType;
	String relTypeCode;
	private int relId;
	private LocalDateTime paymentTime;
	@ManyToOne(fetch = LAZY)
	private PayoutMember payer;
	@ManyToOne(fetch = LAZY)
	private PayoutMember payee;
	private long amount;

	public PayoutItem(Payout payout, PayoutEventType eventType, String relTypeCode, int relId, LocalDateTime payTime, PayoutMember payer, PayoutMember payee, long amount) {
		this.payout = payout;
		this.eventType = eventType;
		this.relTypeCode = relTypeCode;
		this.relId = relId;
		this.paymentTime = payTime;
		this.payer = payer;
		this.payee = payee;
		this.amount = amount;
	}

}
