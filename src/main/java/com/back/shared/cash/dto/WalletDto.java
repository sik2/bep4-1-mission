package com.back.shared.cash.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(
        onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
)
@Getter
public class WalletDto {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final Long holderId;
    private final String holderName;
    private final long balance;


}