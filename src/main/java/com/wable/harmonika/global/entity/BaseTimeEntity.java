package com.wable.harmonika.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// 공통 매핑 정보가 필요할 때 사용한다.
// JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, 필드들도 컬럼으로 인식하도록 설정
// @MappedSuperclass가 붙은 클래스는 엔티티도 아니고, 테이블 매핑도 안된다.
// 참고로 엔티티는 엔티티(@Entity)나 @MappedSuperclass로 지정한 클래스만 상속이 가능
@EntityListeners(AuditingEntityListener.class)
// BaseTimeEntity에 Auditing 기능을 포함시킨다.
// Audit이란, Spring Date JPA에서 시간에 대해 자동으로 값을 넣어주는 기능
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false) // 해당 컬럼은 수정되지 않는다는 의미
    // update 시에 Null 되는 경우 방지
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable=false) // 해당 컬럼은 삽입에서 제외된다는 의미(DB Insert에서 제외). 삽입 시점에서는 비워져있어야하므로 false
    // 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime updatedAt;
}