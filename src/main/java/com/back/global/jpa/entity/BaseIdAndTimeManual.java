package com.back.global.jpa.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseIdAndTimeManual extends BaseIdAndTime {
    @Id
    private int id;

    // 복제 데이터 (참조용)에서 자동으로 시간이 등록되면 안되기 때문에
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
