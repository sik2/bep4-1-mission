package com.back.shared.payout.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.CanGetModelTypeCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PayoutDto implements CanGetModelTypeCode {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private int payeeId;
	private String payeeName;
	private LocalDateTime payoutTime;
	private long amount;
	private boolean isPayeeSystem;

	@Override
	public String getModelTypeCode() {
		return "Payout";
	}
}
