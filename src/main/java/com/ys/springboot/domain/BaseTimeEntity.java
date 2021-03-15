package com.ys.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// BaseTimeEntity class는 모든 Entity의 상위 class가 되어 Entity들의
// createdDate, modifiedDate를 자동으로 관리하는 역할을 수행

// @MappedSuperclass
// JPA Entity class들이 BaseTimeEntity를 상속할 경우 필드(createdDate, modifiedDate)들도 Column으로 인식

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
