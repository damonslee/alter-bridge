package com.yaboong.alterbridge.application.common.auditing;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by yaboong on 2019-08-31
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable<T> {
    @CreatedBy
    protected T createdBy;

    @CreatedDate
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @LastModifiedBy
    protected T modifiedBy;

    @LastModifiedDate
    @UpdateTimestamp
    protected LocalDateTime modifiedAt;
}
