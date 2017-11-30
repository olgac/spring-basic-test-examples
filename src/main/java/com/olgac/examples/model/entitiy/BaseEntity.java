package com.olgac.examples.model.entitiy;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@DynamicUpdate
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> {

    @Id
    @GeneratedValue
    private T id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        if (this.deletedAt == null) {
            this.updatedAt = new Date();
        }
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public void setDeleted(boolean deleted) {
        this.deletedAt = deleted ? new Date() : null;
    }
}
