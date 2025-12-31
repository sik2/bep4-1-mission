package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CashMemberCreateEvent {
	private final CashMemberDto cashMemberDto;
}
