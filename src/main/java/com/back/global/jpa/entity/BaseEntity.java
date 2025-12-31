package com.back.global.jpa.entity;

import com.back.global.config.GlobalConfig;
import com.back.standard.modelType.CanGetModelTypeCode;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 베이스 엔티티
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
// 모든 엔티티들의 조상
public abstract class BaseEntity  implements CanGetModelTypeCode {
    public String getModelTypeCode() {
        return this.getClass().getSimpleName();
    }

    protected void publishEvent(Object event) {
        GlobalConfig.getEventPublisher().publish(event);
    }

    // 이 메소드가 여기 선언되는 이유가 뭐지
    // 자식 클래스인 BaseIdAndTime에 있어야 하는 거 아닌가
    // 아직 선언되지 않은(자식에서 선언되는) 필드를 조회하는 메소드가 여기 있는게 맞나
    // 자식 클래스가 공통으로 사용하는 필드니까 여기서 미리 명세를 해준다고 하는데 적절한 설계가 맞나
    public abstract Long getId();
    public abstract LocalDateTime getCreatedAt();
    public abstract LocalDateTime getUpdatedAt();

}